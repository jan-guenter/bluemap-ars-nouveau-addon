/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.adapter.bluemap522;

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
        assertTrue(StateRoutes.facingless(Map.of()));
        assertNull(StateRoutes.sixWay(Map.of("facing", "sideways")));
        assertFalse(StateRoutes.facingless(Map.of("waterlogged", "false")));
    }
}
