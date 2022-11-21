
val taboolibVersion: String by project

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    id("io.izzel.taboolib") version "1.50"
}

taboolib {
    install(
        "common",
        "platform-bukkit",
        "platform-bungee"
    )
    description {
        contributors {
            name("HSDLao_liao")
        }
        dependencies {
            bukkitApi("1.13")
            name("PlaceholderAPI").optional(true)
            name("Vault").optional(true)
        }
    }
    relocate("com.zaxxer.hikari", "com.zaxxer.hikari_4_0_3")
    relocate("me.geekTicket", group.toString())
    classifier = null
    version = taboolibVersion
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.tabooproject.org/repository/releases")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}


dependencies {

    compileOnly(kotlin("stdlib"))
    // Server Core
    compileOnly("redis.clients:jedis:4.2.2")
    compileOnly("com.zaxxer:HikariCP:4.0.3")
    compileOnly("net.md-5:bungeecord-api:1.16-R0.5-SNAPSHOT")
    compileOnly("ink.ptms.core:v11701:11701-minimize:mapped")
    compileOnly("ink.ptms.core:v11701:11701-minimize:universal")
    compileOnly("ink.ptms.core:v11604:11604")

     // Hook Plugins
    compileOnly("me.clip:placeholderapi:2.10.9") { isTransitive = false }

    // Libraries
    compileOnly(fileTree("lib"))
}

