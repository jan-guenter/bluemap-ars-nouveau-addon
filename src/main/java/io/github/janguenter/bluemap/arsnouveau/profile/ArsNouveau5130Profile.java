/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arsnouveau.profile;

import java.util.List;

/** Exact All the Mons 1.2.0 profile `ars-nouveau-5.13.0-mc1.21.1`. */
public final class ArsNouveau5130Profile {

    public static final String PROFILE_ID = "ars-nouveau-5.13.0-mc1.21.1";
    public static final List<ArtifactPin> ARTIFACTS = List.of(
            new ArtifactPin(
                    "arsNouveau",
                    "ars_nouveau",
                    "5.13.0",
                    "ars_nouveau-1.21.1-5.13.0.jar",
                    20_096_005L,
                    "90796df69bfb39b1a9c79edbfa01c2425e5b86aea47dc55ebdcbf30e88f47592"
            )
    );

    private ArsNouveau5130Profile() {
    }
}
