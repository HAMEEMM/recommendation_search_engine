# Run Results

## Environment

| Property | Value                               |
| -------- | ----------------------------------- |
| Date     | 2026-03-03                          |
| Java     | OpenJDK 1.8.0_462 (Temurin)         |
| OS       | Windows 10 (Git Bash terminal)      |
| JAR      | search-engine.jar (fat-jar, 2.2 MB) |

## Command Used

```bash
java -jar search-engine.jar --case C:\cases\<dataset> --out outputs\<dataset>.output.json
```

## Determinism Verification

Each dataset was executed **twice**; outputs were byte-for-byte identical:

```
dataset_1: IDENTICAL (deterministic)
dataset_2: IDENTICAL (deterministic)
dataset_3: IDENTICAL (deterministic)
dataset_4: IDENTICAL (deterministic)
```

---

## dataset_1 Output

```json
{
  "results": [
    {
      "queryId": "Q1",
      "rankedContentIds": ["C8", "C6", "C1", "C9", "C4"]
    },
    {
      "queryId": "Q2",
      "rankedContentIds": ["C7", "C5", "C2", "C4", "C9"]
    }
  ],
  "recommendations": [
    {
      "queryId": "Q1",
      "recommendedContentIds": ["C7", "C2", "C10"]
    },
    {
      "queryId": "Q2",
      "recommendedContentIds": ["C8", "C3", "C6"]
    }
  ],
  "metrics": {
    "queriesProcessed": 2,
    "topK": 5,
    "recommendationCount": 3
  }
}
```

---

## dataset_2 Output

```json
{
  "results": [
    {
      "queryId": "Q1",
      "rankedContentIds": ["C8", "C17", "C2", "C3", "C16"]
    },
    {
      "queryId": "Q2",
      "rankedContentIds": ["C7", "C26", "C21", "C13", "C5"]
    },
    {
      "queryId": "Q3",
      "rankedContentIds": ["C23", "C20", "C21", "C7", "C15"]
    },
    {
      "queryId": "Q4",
      "rankedContentIds": ["C15", "C7", "C17", "C26", "C14"]
    },
    {
      "queryId": "Q5",
      "rankedContentIds": ["C22", "C9", "C14", "C23", "C6"]
    }
  ],
  "recommendations": [
    {
      "queryId": "Q1",
      "recommendedContentIds": ["C27", "C11", "C22"]
    },
    {
      "queryId": "Q2",
      "recommendedContentIds": ["C23", "C28", "C14"]
    },
    {
      "queryId": "Q3",
      "recommendedContentIds": ["C6", "C14", "C18"]
    },
    {
      "queryId": "Q4",
      "recommendedContentIds": ["C23", "C12", "C20"]
    },
    {
      "queryId": "Q5",
      "recommendedContentIds": ["C27", "C30", "C28"]
    }
  ],
  "metrics": {
    "queriesProcessed": 5,
    "topK": 5,
    "recommendationCount": 3
  }
}
```

---

## dataset_3 Output

```json
{
  "results": [
    {
      "queryId": "Q1",
      "rankedContentIds": ["C41", "C8", "C56", "C64", "C65"]
    },
    {
      "queryId": "Q2",
      "rankedContentIds": ["C7", "C55", "C31", "C37", "C26"]
    },
    {
      "queryId": "Q3",
      "rankedContentIds": ["C69", "C55", "C39", "C45", "C23"]
    },
    {
      "queryId": "Q4",
      "rankedContentIds": ["C63", "C39", "C31", "C15", "C55"]
    },
    {
      "queryId": "Q5",
      "rankedContentIds": ["C9", "C46", "C23", "C22", "C70"]
    },
    {
      "queryId": "Q6",
      "rankedContentIds": ["C22", "C70", "C46", "C10", "C58"]
    },
    {
      "queryId": "Q7",
      "rankedContentIds": ["C60", "C36", "C43", "C12", "C67"]
    },
    {
      "queryId": "Q8",
      "rankedContentIds": ["C28", "C60", "C42", "C12", "C4"]
    },
    {
      "queryId": "Q9",
      "rankedContentIds": ["C27", "C55", "C39", "C75", "C51"]
    },
    {
      "queryId": "Q10",
      "rankedContentIds": ["C54", "C37", "C13", "C61", "C30"]
    }
  ],
  "recommendations": [
    {
      "queryId": "Q1",
      "recommendedContentIds": ["C17", "C14", "C1"]
    },
    {
      "queryId": "Q2",
      "recommendedContentIds": ["C68", "C63", "C23"]
    },
    {
      "queryId": "Q3",
      "recommendedContentIds": ["C21", "C13", "C74"]
    },
    {
      "queryId": "Q4",
      "recommendedContentIds": ["C68", "C23", "C60"]
    },
    {
      "queryId": "Q5",
      "recommendedContentIds": ["C65", "C60", "C33"]
    },
    {
      "queryId": "Q6",
      "recommendedContentIds": ["C71", "C14", "C23"]
    },
    {
      "queryId": "Q7",
      "recommendedContentIds": ["C28", "C20", "C29"]
    },
    {
      "queryId": "Q8",
      "recommendedContentIds": ["C68", "C52", "C5"]
    },
    {
      "queryId": "Q9",
      "recommendedContentIds": ["C11", "C60", "C56"]
    },
    {
      "queryId": "Q10",
      "recommendedContentIds": ["C6", "C22", "C14"]
    }
  ],
  "metrics": {
    "queriesProcessed": 10,
    "topK": 5,
    "recommendationCount": 3
  }
}
```

---

## dataset_4 Output

```json
{
  "results": [
    {
      "queryId": "Q1",
      "rankedContentIds": ["C120", "C41", "C8", "C104", "C89"]
    },
    {
      "queryId": "Q2",
      "rankedContentIds": ["C103", "C7", "C55", "C79", "C117"]
    },
    {
      "queryId": "Q3",
      "rankedContentIds": ["C69", "C111", "C55", "C116", "C93"]
    },
    {
      "queryId": "Q4",
      "rankedContentIds": ["C87", "C135", "C111", "C103", "C63"]
    },
    {
      "queryId": "Q5",
      "rankedContentIds": ["C9", "C46", "C23", "C81", "C142"]
    },
    {
      "queryId": "Q6",
      "rankedContentIds": ["C22", "C106", "C70", "C46", "C10"]
    },
    {
      "queryId": "Q7",
      "rankedContentIds": ["C60", "C84", "C91", "C36", "C115"]
    },
    {
      "queryId": "Q8",
      "rankedContentIds": ["C60", "C148", "C28", "C36", "C125"]
    },
    {
      "queryId": "Q9",
      "rankedContentIds": ["C92", "C71", "C55", "C27", "C111"]
    },
    {
      "queryId": "Q10",
      "rankedContentIds": ["C126", "C133", "C37", "C54", "C13"]
    },
    {
      "queryId": "Q11",
      "rankedContentIds": ["C120", "C144", "C88", "C32", "C41"]
    },
    {
      "queryId": "Q12",
      "rankedContentIds": ["C103", "C31", "C117", "C26", "C55"]
    },
    {
      "queryId": "Q13",
      "rankedContentIds": ["C79", "C93", "C23", "C135", "C116"]
    },
    {
      "queryId": "Q14",
      "rankedContentIds": ["C87", "C15", "C135", "C111", "C63"]
    },
    {
      "queryId": "Q15",
      "rankedContentIds": ["C9", "C14", "C23", "C105", "C126"]
    }
  ],
  "recommendations": [
    {
      "queryId": "Q1",
      "recommendedContentIds": ["C48", "C2", "C3"]
    },
    {
      "queryId": "Q2",
      "recommendedContentIds": ["C31", "C140", "C68"]
    },
    {
      "queryId": "Q3",
      "recommendedContentIds": ["C117", "C45", "C21"]
    },
    {
      "queryId": "Q4",
      "recommendedContentIds": ["C31", "C15", "C143"]
    },
    {
      "queryId": "Q5",
      "recommendedContentIds": ["C65", "C121", "C129"]
    },
    {
      "queryId": "Q6",
      "recommendedContentIds": ["C94", "C150", "C142"]
    },
    {
      "queryId": "Q7",
      "recommendedContentIds": ["C28", "C12", "C140"]
    },
    {
      "queryId": "Q8",
      "recommendedContentIds": ["C84", "C132", "C12"]
    },
    {
      "queryId": "Q9",
      "recommendedContentIds": ["C20", "C140", "C60"]
    },
    {
      "queryId": "Q10",
      "recommendedContentIds": ["C150", "C102", "C14"]
    },
    {
      "queryId": "Q11",
      "recommendedContentIds": ["C48", "C2", "C3"]
    },
    {
      "queryId": "Q12",
      "recommendedContentIds": ["C79", "C7", "C87"]
    },
    {
      "queryId": "Q13",
      "recommendedContentIds": ["C103", "C55", "C31"]
    },
    {
      "queryId": "Q14",
      "recommendedContentIds": ["C39", "C31", "C92"]
    },
    {
      "queryId": "Q15",
      "recommendedContentIds": ["C65", "C121", "C129"]
    }
  ],
  "metrics": {
    "queriesProcessed": 15,
    "topK": 5,
    "recommendationCount": 3
  }
}
```
