package com.mayo.searchengine.engine;

import com.mayo.searchengine.model.*;
import com.mayo.searchengine.output.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Deterministic content search, ranking, and recommendation engine.
 *
 * Scoring (ranking):
 *   1. Keyword Match  – for each query token present in title tokens: +keywordMatch
 *   2. Tag Match      – for each query token that matches a tag (case-insensitive): +tagMatch
 *   3. Specialty Boost – if content.specialty ∈ user.preferredSpecialties: +specialtyBoost
 *   4. Channel Boost  – if content.channel == user.channelPreference: +channelBoost
 *   5. Popularity     – popularityScore * popularityWeight
 *   6. Freshness      – (1 / freshnessDays) * freshnessWeight * 100
 *
 * Tie-break (mandatory, in order):
 *   1. Lower freshnessDays  (ascending)
 *   2. Higher popularityScore (descending)
 *   3. Lexicographically smaller contentId (ascending)
 *
 * Recommendation scoring (for items excluded from ranked results and recentContentIds):
 *   – +tagMatch for each shared tag with the top-ranked result
 *   – +specialtyBoost if specialty matches user preference
 *   – +popularityScore * popularityWeight
 *   – +(1 / freshnessDays) * freshnessWeight * 100
 */
public class SearchEngine {

    private final List<Content> contents;
    private final Map<String, User> userMap;
    private final Rules rules;

    public SearchEngine(List<Content> contents, List<User> users, Rules rules) {
        this.contents = new ArrayList<>(contents);
        this.userMap = new LinkedHashMap<>();
        for (User u : users) {
            userMap.put(u.userId, u);
        }
        this.rules = rules;
    }

    public Output process(List<Query> queries) {
        List<Result> results = new ArrayList<>();
        List<Recommendation> recommendations = new ArrayList<>();

        for (Query query : queries) {
            User user = userMap.get(query.userId);
            List<String> queryTokens = tokenize(query.queryText);

            // ── Ranking ──────────────────────────────────────────────────────────
            List<ScoredContent> scored = new ArrayList<>();
            for (Content c : contents) {
                double score = computeRankingScore(c, queryTokens, user);
                scored.add(new ScoredContent(c, score));
            }
            scored.sort(this::compareDesc);

            List<ScoredContent> topKItems = scored.subList(0, Math.min(rules.topK, scored.size()));
            List<String> rankedIds = topKItems.stream()
                    .map(sc -> sc.content.contentId)
                    .collect(Collectors.toList());

            results.add(new Result(query.queryId, rankedIds));

            // ── Recommendations ──────────────────────────────────────────────────
            // Exclusion set: topK results + user's recently viewed content
            Set<String> excluded = new HashSet<>(rankedIds);
            if (user != null && user.recentContentIds != null) {
                excluded.addAll(user.recentContentIds);
            }

            // Collect the uppercase tags of the #1 ranked item for tag-overlap scoring
            Set<String> topTags = new HashSet<>();
            if (!topKItems.isEmpty() && topKItems.get(0).content.tags != null) {
                for (String t : topKItems.get(0).content.tags) {
                    topTags.add(t.toUpperCase());
                }
            }

            List<ScoredContent> recScored = new ArrayList<>();
            for (Content c : contents) {
                if (!excluded.contains(c.contentId)) {
                    double score = computeRecommendationScore(c, topTags, user);
                    recScored.add(new ScoredContent(c, score));
                }
            }
            recScored.sort(this::compareDesc);

            int recCount = Math.min(rules.recommendationCount, recScored.size());
            List<String> recIds = recScored.subList(0, recCount).stream()
                    .map(sc -> sc.content.contentId)
                    .collect(Collectors.toList());

            recommendations.add(new Recommendation(query.queryId, recIds));
        }

        Metrics metrics = new Metrics(queries.size(), rules.topK, rules.recommendationCount);
        return new Output(results, recommendations, metrics);
    }

    // ── Scoring helpers ───────────────────────────────────────────────────────

    private double computeRankingScore(Content c, List<String> queryTokens, User user) {
        Weights w = rules.weights;
        double score = 0.0;

        // 1. Keyword Match – per query token found in title token set
        Set<String> titleTokens = new HashSet<>(tokenize(c.title));
        for (String qt : queryTokens) {
            if (titleTokens.contains(qt)) {
                score += w.keywordMatch;
            }
        }

        // 2. Tag Match – per query token that matches any tag (case-insensitive)
        Set<String> tagLower = new HashSet<>();
        if (c.tags != null) {
            for (String t : c.tags) tagLower.add(t.toLowerCase());
        }
        for (String qt : queryTokens) {
            if (tagLower.contains(qt)) {
                score += w.tagMatch;
            }
        }

        // 3. Specialty Boost
        if (user != null && user.preferredSpecialties != null
                && user.preferredSpecialties.contains(c.specialty)) {
            score += w.specialtyBoost;
        }

        // 4. Channel Boost
        if (user != null && c.channel != null
                && c.channel.equals(user.channelPreference)) {
            score += w.channelBoost;
        }

        // 5. Popularity
        score += c.popularityScore * w.popularityWeight;

        // 6. Freshness
        score += (1.0 / c.freshnessDays) * w.freshnessWeight * 100.0;

        return score;
    }

    private double computeRecommendationScore(Content c, Set<String> topTags, User user) {
        Weights w = rules.weights;
        double score = 0.0;

        // Tag overlap with #1 ranked result
        if (c.tags != null) {
            for (String tag : c.tags) {
                if (topTags.contains(tag.toUpperCase())) {
                    score += w.tagMatch;
                }
            }
        }

        // Specialty Boost
        if (user != null && user.preferredSpecialties != null
                && user.preferredSpecialties.contains(c.specialty)) {
            score += w.specialtyBoost;
        }

        // Popularity
        score += c.popularityScore * w.popularityWeight;

        // Freshness (same formula)
        score += (1.0 / c.freshnessDays) * w.freshnessWeight * 100.0;

        return score;
    }

    // ── Comparator: descending score, mandatory tie-break ─────────────────────

    private int compareDesc(ScoredContent a, ScoredContent b) {
        // Primary: score descending
        int cmp = Double.compare(b.score, a.score);
        if (cmp != 0) return cmp;

        // Tie-break 1: lower freshnessDays ascending
        cmp = Double.compare(a.content.freshnessDays, b.content.freshnessDays);
        if (cmp != 0) return cmp;

        // Tie-break 2: higher popularityScore descending
        cmp = Double.compare(b.content.popularityScore, a.content.popularityScore);
        if (cmp != 0) return cmp;

        // Tie-break 3: lexicographically smaller contentId ascending
        return a.content.contentId.compareTo(b.content.contentId);
    }

    // ── Tokenizer ─────────────────────────────────────────────────────────────

    private List<String> tokenize(String text) {
        if (text == null || text.isEmpty()) return Collections.emptyList();
        return Arrays.asList(text.toLowerCase().split("\\s+"));
    }

    // ── Internal ──────────────────────────────────────────────────────────────

    private static final class ScoredContent {
        final Content content;
        final double score;

        ScoredContent(Content content, double score) {
            this.content = content;
            this.score = score;
        }
    }
}
