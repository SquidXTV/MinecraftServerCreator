package com.squidxtv.msc.server.spigot;

public enum SpigotVersion {
    S1_19("1.19"),
    S1_18_2("1.18.2"),
    S1_18_1("1.18.1"),
    S1_18("1.18"),
    S1_17_1("1.17.1"),
    S1_17("1.17"),
    S1_16_5("1.16.5"),
    S1_16_4("1.16.4"),
    S1_16_3("1.16.3"),
    S1_16_2("1.16.2"),
    S1_16_1("1.16.1"),
    S1_15_2("1.15.2"),
    S1_15_1("1.15.1"),
    S1_15("1.15"),
    S1_14_4("1.14.4"),
    S1_14_3("1.14.3"),
    S1_14_2("1.14.2"),
    S1_14_1("1.14.1"),
    S1_14("1.14"),
    S1_13_2("1.13.2"),
    S1_13_1("1.13.1"),
    S1_13("1.13"),
    S1_12_2("1.12.2"),
    S1_12_1("1.12.1"),
    S1_12("1.12"),
    S1_11_2("1.11.2"),
    S1_11_1("1.11.1"),
    S1_11("1.11"),
    S1_10_2("1.10.2"),
    S1_9_4("1.9.4"),
    S1_9_2("1.9.2"),
    S1_9("1.9"),
    S1_8_8("1.8.8"),
    S1_8_3("1.8.3"),
    S1_8("1.8");

    private final String build;

    SpigotVersion(String build) {
        this.build = build;
    }

    public String getBuild() {
        return this.build;
    }

    public static SpigotVersion fromVersion(String s) {
        return SpigotVersion.valueOf(s.replace("Spigot - ", "S").replace(".", "_"));
    }
}
