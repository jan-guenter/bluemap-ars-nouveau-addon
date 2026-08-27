/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.adapter.bluemap522;

import java.util.Map;
import java.util.Set;

/** Strict legal-state selectors for the eleven owned Ars Nouveau hosts. */
final class StateRoutes {

    static final Set<String> FACINGS = Set.of(
            "north", "south", "west", "east", "up", "down"
    );

    private StateRoutes() {
    }

    static String sixWay(Map<String, String> properties) {
        String facing = properties.get("facing");
        return properties.size() == 1 && FACINGS.contains(facing) ? facing : null;
    }

    static boolean facingless(Map<String, String> properties) {
        return properties.isEmpty();
    }
}
