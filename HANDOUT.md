# Lab: The Debugger Is Not a Punishment

### The one rule

> **You may not add a single `System.out.println` to any file in this lab.**
>
> The print statements already in the code stay. You add nothing. Everything you
> discover here, you discover by asking the running program questions.

If that sounds like an artificial handicap, keep a running tally as you work: every
time you catch yourself thinking *"I'd just print it here,"* write down what you
would have printed. When you're done, look at that list and notice that in at least
three cases, printing would have shown you nothing useful at all.

### Before you start

Watch the pre-class video and import the `DebuggerLab` project.
You should be able to set a breakpoint, launch under the debugger,
and use step over / step into / step return without looking anything up.

### Smoke test

1. Open `Station1.java`.
2. Put a breakpoint on the first line of `main` and press **F11**.
3. Confirm the program stops there and you can see a **Variables** view.

If that works, you're ready. If it doesn't, message me before you go any further —
don't burn your energy fighting the setup instead of doing the interesting part.

### One thing to set up first: seeing `static` fields

Several files in this lab keep their data in `static` fields — `Station1.java` has
`static int[] scores`, and all of `Roster.java` is built this way.

**The Variables view does not show static fields.** It shows the local variables of
whichever stack frame you have selected, and that's all. So if this handout tells you
to look at `scores` and there is no `scores` anywhere in the Variables view, nothing
is broken and you haven't missed a step. You're looking in a panel that structurally
cannot show it.

Here is how you see them. The second option always works:

**Option A — turn them on.** In the **Variables** view, open the view menu (the **⋮**
or **▽** icon in that view's own toolbar) ▸ **Java** ▸ **Show Static Variables**. The
exact wording moves around between Eclipse versions; look for the entry with "static"
in it.

**Option B — the Expressions view.** *Window ▸ Show View ▸ Expressions*, then
**Add new expression** and type the field name qualified by its class:

```java
Station1.scores
```

It displays just like a Variables entry, id number and all, and it *stays on screen
while you step*. Anywhere this handout says "look at `<some static field>`," this is
your move.

Option B is worth learning regardless. The Expressions view evaluates any Java
expression you type — `scores[617]`, `names[0].equals(built)`, `size < 0` — against
the paused program. It is the closest thing the debugger has to a conversation.

### The controls, for reference

| Key | Action | What it means |
|---|---|---|
| **F5** | Step Into | Go *inside* the method being called |
| **F6** | Step Over | Run the whole line, don't descend into calls |
| **F7** | Step Return | Finish this method, come back to the caller |
| **F8** | Resume | Run until the next breakpoint |
| **Ctrl+F2** | Terminate | Kill it |

---

# The guided tour

Six stations. Work through them in order — each one sets up the next.

**Stations 4, 5, and 6 are the ones that matter most.** Each teaches something that
print statements cannot do *at all* — not slower, not clumsier, cannot. If you only
get part way through, make sure you get to those.

---

## Station 1 — Warm-up, and one thing you've never noticed

Open `Station1.java`. Nothing here is broken.

1. Breakpoint on `int total = sum(scores);`. Debug it (**F11**).
2. Press **F5** to step *into* `sum`.
3. Look at the **Debug** view (usually top-left). You now see two stack frames:
   `sum` sitting on top of `main`. Click back and forth between them and watch the
   Variables view change. *That is the call stack. You get it for free, always.*
4. Press **F6** a few times and watch `running` climb in the Variables view.
5. Press **F7** to finish `sum` and land back in `main`.

Now the part nobody notices:

6. Press **F5** again to get back inside `sum`. In the Variables view you see
   `values`, displayed as something like `int[5] (id=17)`.
7. `scores` is a `static` field, so it will **not** appear in the Variables view (see
   the setup section above). Add it to the **Expressions** view instead:

   ```java
   Station1.scores
   ```

Both arrays are now on screen at the same time, each with its own id.

**Checkpoint 1.** Write down the id number shown for `values` and the id number
shown for `Station1.scores`. What do you notice? What does that tell you about what
actually got passed to `sum`?

> **If you don't see `(id=…)`:** in the **Expressions** view
> (*Window ▸ Show View ▸ Expressions*), add the expression
> `System.identityHashCode(scores)`. That number is the same idea — a unique
> fingerprint for one object in memory. Use it anywhere this handout says "id".

---

## Station 2 — Two things that print the same

Open `Station2.java`.

1. Run it **normally** first (*Run ▸ Run As ▸ Java Application*). Write down the
   three lines of output.
2. Both `typed` and `built` printed `Ada`. **Predict, in writing**, what
   `sameAsTyped` and `sameAsBuilt` will be. Commit to an answer before you continue.
   You will not learn anything from this station if you skip this step.
3. Breakpoint on the line `boolean sameAsTyped = ...`. Debug it.
4. In the Variables view, find `typed` and `built`. **Write down both id numbers.**
5. `names` is a `static` field, so add `Station2.names` to the **Expressions** view.
   Expand it and write down the id of `names[0]`.
6. Press **F6** three times and read the three booleans.

**Checkpoint 2.** Both variables printed `Ada`. One `==` comparison came out true
and the other false. Using the id numbers you recorded, explain why.

Then answer the real question: **could you have found this with print statements
alone?** Be specific — say what `println` would have shown you, and what it hid.

---

## Station 3 — Who else is holding this array?

Open `Station3.java`. In C you could see references coming, because you had to type
`*` and `&`. In Java they are invisible. Here is how you make them visible again.

1. Breakpoint on `alias[0] = 999;`. Debug it.
2. In the Variables view, note the ids of `original`, `alias`, and `copy`.
   **Two of these three are the same object.** Which two?
3. Step over (**F6**) twice. Expand all three arrays and watch which ones changed.
4. Keep stepping. When you reach `curveAll(original, 5);`, step **into** it (**F5**)
   and watch `original` change *from inside another method*.
5. Step until you get past `addTen(firstScore);`.

**Checkpoint 3.** `curveAll` successfully changed the caller's array. `addTen`
completely failed to change the caller's `int`. Both look like "pass a thing to a
method and modify it." Explain the difference — and explain how the Variables view
showed you the difference before you finished reading the code.

---

## Station 4 — One bad number in a thousand

Open `Station4.java`. The registrar's spreadsheet says this section averaged about
**77**. Run the program. It reports **59**.

Somewhere in 1000 scores something is wrong. You cannot print them all. (Well — you
could. Hold that thought for the checkpoint.)

1. Put a breakpoint inside the summing loop in `main`, on the line
   `total = total + scores[i];`.
2. Debug it. It stops immediately — on iteration 0, which is useless. **Terminate.**
3. Now the good part. **Right-click the breakpoint marker** in the left margin ▸
   **Breakpoint Properties…**
4. Check **Conditional**, and in the box type:

   ```java
   scores[i] < 0
   ```

   Leave it on *suspend when true*. Click OK.
5. Debug again.

The program now runs at full speed and stops on precisely the iteration you care
about.

6. Read `i` in the Variables view — it's a local, so it's right there. **Write it
   down.** To see the offending score itself, add this to the **Expressions** view:

   ```java
   Station4.scores[i]
   ```

   (`scores` is static, so the Variables view won't list it. The breakpoint
   *condition* had no such trouble — condition expressions can reach static fields
   perfectly well. It is only the Variables *display* that leaves them out.)
7. Press **F8** (Resume) a few times to find out whether it's the only one. Your
   Expressions entry re-evaluates at every stop, so it will track each new `i`.

**Checkpoint 4.** Record the index of the first bad score and its value. Then:
if you had solved this by printing every score, how many lines of output would you
have scrolled through to find it? What is that number for a real roster of 40,000
students? Compare that to what the conditional breakpoint cost you.

---

## Station 5 — Who is changing `size`? *(the important one)*

Open `Station5.java`. We add four students, remove one, tidy up, and end up with a
roster whose size is **negative**.

Four different methods touch `size`: `add`, `removeAt`, `compact`, and `main`. With
print statements you would have to edit every single one of them, then read the
output and try to reconstruct the order of events.

Instead you are going to ask the JVM to stop the program the instant anyone touches
that variable.

1. In `Station5.java`, find the field declaration `static int size = 0;`
2. **Double-click in the left margin next to that line.** (Or right-click the field
   name ▸ *Toggle Watchpoint*.) You get a breakpoint marker with a small extra
   decoration. That is a **watchpoint**.
3. Debug it (**F11**).

The program stops every single time `size` is written.

Before you go further, add `Station5.size` to the **Expressions** view — it's a
static field, so the Variables view won't show it, and watching the number change at
each stop is the whole point of this station.

4. Each time it stops, look at the **Debug** view. The top frame tells you *which
   method* is doing the writing. Press **F8** to continue. Do this a few times and
   watch the culprit list build up: `add`, `add`, `add`, `add`, `removeAt`…
5. That is a lot of resuming. Let's aim. **Terminate.** Right-click the watchpoint ▸
   **Breakpoint Properties…** ▸ check **Conditional** ▸ enter:

   ```java
   size < 0
   ```
6. Debug again.

The program now runs until the exact moment `size` becomes impossible, and stops there.

7. Look at the **Debug** view and read the call stack from the top down. It tells you
   the method that did it *and* who called that method.

**Checkpoint 5.** Name the method that corrupts `size` and explain in one sentence
what it is doing wrong. Then answer this: to find this bug with print statements,
how many methods would you have had to edit, and how would you have known which
`size = ...` line printed which value?

---

## Station 6 — Rewinding time

Open `Station6.java`. Nothing is broken. This station exists to show you a feature
with no print-statement equivalent whatsoever: **you can un-run code.**

1. Breakpoint on the first line inside `largestMultipleOf`. Debug it.
2. Step through the whole loop with **F6** until you reach `return best;`.
3. You are now past the interesting part. Normally you would restart the program.
   Don't.
4. In the **Debug** view, **click the `largestMultipleOf` frame to select it**, then
   **Run ▸ Drop to Frame** (there is also a toolbar button in the Debug view).

You are back at the top of the method. The loop has un-run. `best` is uninitialized again.

5. Now run an experiment. In the Variables view, **double-click the value of `k`**
   (or right-click ▸ *Change Value…*) and set it to `5`.
6. Step through the loop again and watch what `best` becomes now.

You just ran a method twice with different inputs, without restarting the program,
without recompiling, and without editing a single line of code.

**Checkpoint 6.** What did the method return with `k = 3`, and what did it return
with `k = 5`? Describe one situation in a program you have actually written this
semester where "rewind and try again" would have saved you effort.

---

# The bug hunt

Open `Roster.java`. It stores a class roster as **parallel arrays** — `names[i]`,
`ids[i]`, and `scores[i]` all describe the same student. This is how you build a
record before you have objects.

> **Every field in `Roster` is `static`**, so none of them will appear in the
> Variables view. Park these in the **Expressions** view before you start and leave
> them there for the whole bug hunt:
>
> ```java
> Roster.size
> Roster.names
> Roster.ids
> Roster.scores
> ```
>
> You will be comparing ids between these and other values constantly. Having them
> permanently on screen is the difference between a pleasant hunt and a miserable one.

Run `RosterTests.java` as a Java Application. You will see something like:

```
FAIL  1  highestAverage   threw NullPointerException
pass  2  findByName with a literal
FAIL  3  findByName with a typed-in name
pass  4  findById, small ID
FAIL  5  findById, large ID
FAIL  6  backupScores is independent
FAIL  7  removeAllFailing

passed: 2   failed: 5
```

**Five failures, five separate bugs.** Fix all five.

Two warnings, and I mean these sincerely:

- **Tests 2 and 4 pass. They are lying to you.** The code they exercise is broken.
  It just happens to be broken in a way that particular input can't reveal. When you
  find the bugs behind tests 3 and 5, look hard at why the *passing* version passed.
  That is the single most important thing in this lab.
- **Do not fix by inspection.** Several of these are things you will "explain" wrong
  if you only read the source. For every bug, stop the program at the moment it goes
  wrong and *look at the actual values*. If your explanation doesn't match what the
  Variables view shows, your explanation is wrong.

### Make the test part of the debugging!

When you know a test fails, that is usually where you can set your breakpoint. 
Note that this means without written tests, it is difficult to leverage the full power of a debugger!

---

## What to submit

A printed report with a copy submitted on Moodle as well as your Roster.java file.

## Report should contain:

**Your written answers to Checkpoints 1–6.**

**One bug report per bug, five total.** Each one:

1. **Which test failed**, and the symptom in one sentence.
2. **Where you set your breakpoint or watchpoint, and why you chose that spot.**
   (Not "I put a breakpoint in the method." Why *that line*?)
3. **A screenshot of the Variables or Debug view at the moment of truth** — the
   instant where the wrong value is visible on screen. This is your evidence. A
   screenshot of the test output is not evidence.
4. **Root cause, in one sentence.**
5. **The fix** — paste the before and after lines.
6. **"Why would print statements have been slow or misleading here?"** One or two
   sentences. If your honest answer is "they wouldn't have been, printing would have
   been fine," *say so* — for one or two of these bugs that is a defensible answer,
   and I would rather have your real opinion than the answer you think I want.

Plus your fixed `Roster.java`, with all seven tests passing.

### One last thing

If you added a print statement at any point, that's OK — just tell me where and why.
Knowing exactly when the debugger was *not* worth the trouble is a real skill too,
and I'd rather you be honest about it than quietly delete the evidence.
