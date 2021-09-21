import kotlinx.cinterop.*
import raylib.*



    var MAX_BUNNIES = 500000
    var MAX_BATCH_ELEMENTS = 8192
    fun bunnymark(args: Array<String?>?) {
        // Initialization
        //--------------------------------------------------------------------------------------
        val screenWidth = 1920
        val screenHeight = 1080
        InitWindow(screenWidth, screenHeight, "raylib [textures] example - bunnymark")

        // Load bunny texture
        val texBunny = LoadTexture("resources/wabbit_alpha.png")
        val bunnies = arrayOfNulls<Bunny>(MAX_BUNNIES)
        for (i in 0 until MAX_BUNNIES) {
            bunnies[i] = Bunny()
        }
        var bunniesCount = 0 // Bunnies counter
        SetTargetFPS(60) // Set our game to run at 60 frames-per-second
        //--------------------------------------------------------------------------------------

        // Main game loop
        while (!WindowShouldClose()) // Detect window close button or ESC key
        {
            // Update
            //----------------------------------------------------------------------------------
            if (IsMouseButtonDown(MOUSE_LEFT_BUTTON.toInt())) {
                // Create more bunnies
                for (i in 0..99) {
                    if (bunniesCount < MAX_BUNNIES) {
                        bunnies[bunniesCount]!!.position.x = GetMousePosition().useContents { x }
                        bunnies[bunniesCount]!!.position.y = GetMousePosition().useContents { y }
                        bunnies[bunniesCount]!!.speed.x = (GetRandomValue(-250, 250) / 60.0f)
                        bunnies[bunniesCount]!!.speed.y = (GetRandomValue(-250, 250) / 60.0f)
                        bunnies[bunniesCount]!!.color = cValue<Color> {
                            r = GetRandomValue(50, 240).toUByte()
                            g = GetRandomValue(80, 240).toUByte()
                            b = GetRandomValue(100, 240).toUByte()
                            a = 255u
                        }
                        bunniesCount++
                    }
                }
            }

            // Update bunnies
            for (i in 0 until bunniesCount) {
                bunnies[i]!!.position.x += bunnies[i]!!.speed.x
                bunnies[i]!!.position.y += bunnies[i]!!.speed.y
                if (bunnies[i]!!.position.x + texBunny.useContents { width} / 2.0 > screenWidth ||
                    bunnies[i]!!.position.x + texBunny.useContents {  2.0 } < 0
                ) {
                    bunnies[i]!!.speed.x *= -1f
                }
                if (bunnies[i]!!.position.y + texBunny.useContents { height} / 2.0 > screenHeight ||
                    bunnies[i]!!.position.y + texBunny.useContents { height} / 2.0 - 40 < 0
                ) {
                    bunnies[i]!!.speed.y *= -1f
                }
            }
            //----------------------------------------------------------------------------------

            // Draw
            //----------------------------------------------------------------------------------
            BeginDrawing()
            ClearBackground(RAYWHITE)
            for (i in 0 until bunniesCount) {
                // NOTE: When internal batch buffer limit is reached (MAX_BATCH_ELEMENTS),
                // a draw call is launched and buffer starts being filled again;
                // before issuing a draw call, updated vertex data from internal CPU buffer is send to GPU...
                // Process of sending data is costly and it could happen that GPU data has not been completely
                // processed for drawing while new data is tried to be sent (updating current in-use buffers)
                // it could generates a stall and consequently a frame drop, limiting the number of drawn bunnies
                DrawTexture(
                    texBunny,
                    bunnies[i]!!.position.x.toInt(),
                    bunnies[i]!!.position.y.toInt(),
                    bunnies[i]!!.color
                )

            }
            DrawRectangle(0, 0, screenWidth, 40, BLACK)
            DrawText("bunnies: $bunniesCount", 120, 10, 20, GREEN)
            DrawText("batched draw calls: " + 1 + bunniesCount / MAX_BATCH_ELEMENTS, 320, 10, 20, MAROON)
            DrawFPS(10, 10)
            EndDrawing()
            //----------------------------------------------------------------------------------
        }

        // De-Initialization
        //--------------------------------------------------------------------------------------
        UnloadTexture(texBunny) // Unload bunny texture
        CloseWindow() // Close window and OpenGL context
        //--------------------------------------------------------------------------------------
    }

    internal class Bunny {
        var position = Vec2()
        var speed = Vec2()
        var color = cValue<Color>{}
    }

    internal class Vec2 {
        var x = 0f
        var y = 0f
    }

    internal class Col {
        var r = 0
        var g = 0
        var b = 0
        var a = 0
    }
