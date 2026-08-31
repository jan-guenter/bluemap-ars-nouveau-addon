/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.adapter.bluemap523;

import java.util.Map;
import java.util.Set;

/** Strict legal-state selectors for the eleven owned Ars Nouveau hosts. */
final class StateRoutes {

    static final Set<String> FACINGS = Set.of(
            "north", "south", "west", "east", "up", "down"
    );
    private static final Set<String> BOOLEANS = Set.of("false", "true");

    private StateRoutes() {
    }

    static String sixWay(Map<String, String> properties) {
        String facing = properties.get("facing");
        return properties.size() == 1 && FACINGS.contains(facing) ? facing : null;
    }

    static boolean relay(Map<String, String> properties) {
        return properties.size() == 1
                && BOOLEANS.contains(properties.get("waterlogged"));
    }

    static String turret(Map<String, String> properties) {
        String facing = properties.get("facing");
        return properties.size() == 3
                && FACINGS.contains(facing)
                && BOOLEANS.contains(properties.get("triggered"))
                && BOOLEANS.contains(properties.get("waterlogged"))
                ? facing : null;
    }
}
