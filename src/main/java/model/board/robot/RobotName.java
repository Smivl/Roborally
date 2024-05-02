package model.board.robot;

public enum RobotName {
    TITANUS_PRIME("Titanus Prime"),
    ECHO_VANGUARD("Echo Vanguard"),
    NOVA_SENTINEL("Nova Sentinel"),
    ZENITH_AUTOMATON("Zenith Automaton"),
    PHOENIX_STRYKER("Phoenix Stryker"),
    ASTRAL_ENFORCER("Astral Enforcer");

    private final String name;

    RobotName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

