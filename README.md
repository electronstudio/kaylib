This is an example of using Raylib in Kotlin Native.

I'm hesitant to call it a binding, because Kotlin Native creates the binding itself.
This just shows you how to set it up.  (Copy this project.)

Only tested on Linux, you'd need to change the paths for Windows.

Syntax is not completely pleasant so might be worth doing a wrapper to make it easier to use, but I
don't know how you'd autogenerate that and I don't plan to make one by hand.

Performance is pretty poor.

### Bunnymark

| Library                | Implementation    | Bunnies (60 FPS) | Percentage    |
| -------------          | -------------     | -------------    | ------------- |
| Raylib 3.7             | C                 | 180000           | 100%          |
| Jaylib 3.7 | Java 11         | 39000            | 22%           |
| Jaylib 3.7 | Java 11 Avoiding native calls         | 64000            | 36%           |
| Jaylib 3.7 | Java 17 Avoiding native calls         | 73000            | 41%           |
| Kaylib 3.7 | Kotlin native | 28000 | 16% |