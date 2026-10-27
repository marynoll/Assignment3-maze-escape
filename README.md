# Assignment 3. Maze Escape

Student ID: 252901
Name: Mariya Karle
Group: SE-2519
Maze size (rows x columns): 5x5

This file is the report. Fill it in as you go. How to start: `START_HERE.md`. The task: `docs/Assignment3.pdf`.

## Program output

The 13 lines your program prints on your data (`java -cp out Main`):

    Broken:109
    Kept:90
    Last broken wall:164
    MST cost:29516
    Heaviest broken wall:660
    Path length:35
    DFS visited:87
    Dead ends:32
    Zones:8
    Largest zone:23
    Is DAG:0
    Flow stages:5
    Fastest route cost:3

My maze:

![maze](output/maze.png)

## Questions

Answer in your own words, two or three sentences each.

Mission 0. What do find and union do, and what makes them fast in your code?

Find follows parent links to the root of a cell's set and makes every cell on the way point straight to the root.
Union hangs the smaller tree under the larger one. Together they keep the trees flat so each operation takes constant time

Mission 1. Why is Broken always R*C - 1, and Kept always (R-1)*(C-1)?

The grid starts with RC separate cells and ends as one connected group.
Every successful union merges two groups, so exactly RC - 1 walls are broken.
The total number of walls is R*(C-1) + C*(R-1); subtracting RC - 1 leaves exactly (R-1)(C-1) walls that stay.

Mission 2. My MST cost from Kruskal and from Prim, and why they are equal:

Kruskal gives [MST cost] and Prim gives [MST cost].
Both build a minimum spanning tree, and because all wall costs are different, 
the minimum spanning tree is unique, meaning both algorithms will always calculate the same total.

Mission 3. Why is there exactly one path between two cells? How did you avoid a stack overflow on a huge maze?

The broken walls form a spanning tree: it is connected and it has no cycles, making the path unique.
Stack overflow is avoided by writing an iterative DFS using a manual array as a stack and a "next direction" counter instead of recursion.


Mission 4. Why does the second pass of Kosaraju go on the reversed graph? Why do the slides have no cycle exactly when Zones equals the number of cells?

On the reversed graph, you cannot leave a source zone to another zone, so each search collects exactly one zone.
If there is no cycle, no two cells can reach each other, so every cell becomes its own zone, making the zone count equal to the cell count.
Mission 5. Why is it enough to go through the zones in the order of their numbers? What does this pass cost?
Zone numbers are a topological order. When checking slides leaving a zone, all entering slides are already handled, so its longest chain and cheapest cost are final.
The pass itself is O(m), or O(m log m) with the sort by zone.

## AI use

The syllabus requires you to say which AI tools you used and for what. Write the tool (ChatGPT, Copilot, other) and what you asked it: explain an idea, find an error, review code. Write "none" if you did not use any. If you took code or text from a book or a website, give the source here.

Gemini - I used it to help me understand an algorithm and used it to check my code and help me to fix any errors

## Timing (Mission 7)

Milliseconds, 5 runs each, from `java -cp out Bench`.

| maze        | Kruskal                           | Prim                              |
|-------------|-----------------------------------|-----------------------------------|
| 100 x 100   | 14 8 12 19 17 avg 14              | 22 8 6 7 5 avg 9                  |
| 300 x 300   | 97 72 62 46 43 avg 64             | 79 65 74 75 90 avg 77             |
| 1000 x 1000 | 1082 1071 1108 1140 1059 avg 1092 | 1938 1968 1917 1950 1879 avg 1931 |

What do the numbers say about how the time grows? Two or three sentences.



## Complexity

n is the number of cells, m the number of walls (or slides). Fill in every algorithm you wrote.

| algorithm                | time            | space  | assumptions                              |
|--------------------------|-----------------|--------|------------------------------------------|
| DSU find and union       | O(1) ammortized | O(n)   | union by size and path compression       |
| Kruskal                  | O(mlogm)        | O(m)   | dominated by sorting the walls           |
| Prim                     | O(mlogm)        | O(n+m) | binary heap priority queue, lazy deletion|
| DFS                      | O(n)            | O(n)   | the maze is a tree with n - 1 doors      |
| Kosaraju                 | O(n+m)          | O(n+m) | iterative DFS, two passes                |
| Route in the zone graph  | O(mlogm)        | O(m)   | sort slides by zone, then one pass       |

## Before you submit

- data/walls.txt, data/slides.txt and output/maze.png are in the repository.
- `java tools/Check.java` shows all examples right.
- This README is filled in, the program output and the section AI use included.
- The work is pushed to main (at least one commit per mission) and the repository is public.
- The link is attached in Moodle.
