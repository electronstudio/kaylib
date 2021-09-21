import kotlinx.cinterop.CValue
import kotlinx.cinterop.cValue
import kotlinx.cinterop.useContents
import raylib.*

var LIGHTGRAY = cValue<Color> {
    r = 200u
    g = 200u
    b = 200u
    a = 255u
}

var RED = cValue<Color> {
    r = 230u
    g = 41u
    b = 55u
    a = 255u
}

var VIOLET = cValue<Color> {
    r = 135u
    g = 60u
    b = 190u
    a = 255u
}

var RAYWHITE = cValue<Color> {
    r = 245u
    g = 245u
    b = 245u
    a = 255u
}

var BLACK = cValue<Color> {
    r = 0u
    g = 0u
    b = 0u
    a = 255u
}

var GREEN = cValue<Color> {
    r = 0u
    g = 255u
    b = 0u
    a = 255u
}

var MAROON = cValue<Color> {
    r = 190u
    g = 33u
    b = 55u
    a = 255u
}


//    : Color = com.raylib.Jaylib.c(200, 200, 200, 255)
//var GRAY: Color = com.raylib.Jaylib.c(130, 130, 130, 255)
//var DARKGRAY: Color = com.raylib.Jaylib.c(80, 80, 80, 255)
//var YELLOW: Color = com.raylib.Jaylib.c(253, 249, 0, 255)
//var GOLD: Color = com.raylib.Jaylib.c(255, 203, 0, 255)
//var ORANGE: Color = com.raylib.Jaylib.c(255, 161, 0, 255)
//var PINK: Color = com.raylib.Jaylib.c(255, 109, 194, 255)


//var GREEN: Color = com.raylib.Jaylib.c(0, 228, 48, 255)
//var LIME: Color = com.raylib.Jaylib.c(0, 158, 47, 255)
//var DARKGREEN: Color = com.raylib.Jaylib.c(0, 117, 44, 255)
//var SKYBLUE: Color = com.raylib.Jaylib.c(102, 191, 255, 255)
//var BLUE: Color = com.raylib.Jaylib.c(0, 121, 241, 255)
//var DARKBLUE: Color = com.raylib.Jaylib.c(0, 82, 172, 255)
//var PURPLE: Color = com.raylib.Jaylib.c(200, 122, 255, 255)
//var VIOLET: Color = com.raylib.Jaylib.c(135, 60, 190, 255)
//var DARKPURPLE: Color = com.raylib.Jaylib.c(112, 31, 126, 255)
//var BEIGE: Color = com.raylib.Jaylib.c(211, 176, 131, 255)
//var BROWN: Color = com.raylib.Jaylib.c(127, 106, 79, 255)
//var DARKBROWN: Color = com.raylib.Jaylib.c(76, 63, 47, 255)
//var WHITE: Color = com.raylib.Jaylib.c(255, 255, 255, 255)
//var BLACK: Color = com.raylib.Jaylib.c(0, 0, 0, 255)
//var BLANK: Color = com.raylib.Jaylib.c(0, 0, 0, 0)
//var MAGENTA: Color = com.raylib.Jaylib.c(255, 0, 255, 255)


fun main() {
    println("Hello, Kotlin/Native!")
    InitWindow(800, 450, "Raylib static texture test")
    SetTargetFPS(60)
    val camera = cValue<Camera3D>{
        position.x = 18f
        position.y = 16f
        position.z = 18f
        up.x = 0f
        up.y = 1f
        up.z = 0f
        fovy = 45f
        projection = CAMERA_PERSPECTIVE.toInt()
    }



    val image: CValue<Image> = LoadImage("resources/heightmap.png")
    val texture: CValue<Texture2D> = LoadTextureFromImage(image)
    val mesh: CValue<Mesh> = GenMeshHeightmap(image, cValue<Vector3>{
        x = 16f
        y = 8f
        z = 16f
    })
        val model = LoadModelFromMesh(mesh)

        //model.materials().maps().position(0).texture(texture)

//        model.useContents {
//            materials[]
//        }

        UnloadImage(image)
        SetCameraMode(camera, CAMERA_ORBITAL.toInt())

        while (!WindowShouldClose()) {
            UpdateCamera(camera)
            BeginDrawing()
            ClearBackground(RAYWHITE)
            BeginMode3D(camera)
            DrawModel(model, cValue<Vector3>{
                x = -8f
                    y = 0f
                z = -8f}
            , 1f, RED)
            DrawGrid(20, 1.0f)
            EndMode3D()
            DrawText("This mesh should be textured", 190, 200, 20, VIOLET)
            DrawFPS(20, 20)
            EndDrawing()
        }
        CloseWindow()
    }


