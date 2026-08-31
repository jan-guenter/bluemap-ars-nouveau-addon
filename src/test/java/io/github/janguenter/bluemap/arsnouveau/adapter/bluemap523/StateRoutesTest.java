/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.adapter.bluemap523;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

class StateRoutesTest {

    @Test
    void admitsTheExactInstalledStateShapes() {
        int admitted = 0;
        for (String facing : StateRoutes.FACINGS) {
            if (StateRoutes.sixWay(Map.of("facing", facing)) != null) {
                admitted++;
            }
        }

        assertEquals(6, admitted);
        assertTrue(StateRoutes.relay(Map.of("waterlogged", "false")));
        assertTrue(StateRoutes.relay(Map.of("waterlogged", "true")));
        assertEquals("north", StateRoutes.turret(Map.of(
                "facing", "north", "triggered", "false", "waterlogged", "false"
        )));
        assertNull(StateRoutes.sixWay(Map.of("facing", "sideways")));
        assertFalse(StateRoutes.relay(Map.of()));
        assertNull(StateRoutes.turret(Map.of("facing", "north")));
    }
}
