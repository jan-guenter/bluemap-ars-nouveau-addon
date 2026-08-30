# BlueMap Ars Nouveau Add-on

A Java 21 BlueMap add-on for the exact `ars-nouveau-5.13.0-mc1.21.1` profile in All the Mons
`1.2.0` / Minecraft `1.21.1`.

Version `0.1.0-alpha.2` keeps the owner-accepted renderer while replacing its
private installed-GEO compiler with the pinned shared source module. The exact
artifact gate admits only Ars Nouveau 5.13.0. Eleven block-entity-rendered
hosts use deterministic static meshes compiled from ten operator-installed GEO
resources and their installed textures.

## Build

Clone with `--recurse-submodules`, or initialize an existing checkout with
`git submodule update --init --recursive -- tooling/bluemap-addon-toolkit
modules/bluemap-installed-geo-resource-models`. The settings preflight accepts
only the committed toolkit gitlink at
`6cd34a8368cc4ee8628fbe830a90ec5b14960629` and the Installed-GEO source
module at `c80a83eb6e2cb0bb05a69ace9716ef08b9db14f2`, with Java source tree
`8db87f933557d54c5ede2db70d94f67eaf44c30b`. It rejects an uninitialized,
changed, or dirty checkout.

```bash
gradle --no-daemon -PbluemapSourcePath=../bluemap-backport clean check build
```

`check` is the quick Java/checkstyle/archive gate. `prototypeCheck` additionally
requires the exact Ars Nouveau JAR and validates the 12-case gallery. See
`provenance/upstreams.json` for immutable artifact identities and
the [execution guide](docs/EXECUTION.md) for the prototype-to-release loop.

The build compiles the module's three Java source files directly and declares
Gson 2.8.9 as an explicit compile-only dependency. The module JAR is neither a
runtime dependency nor nested in the add-on. Ars Nouveau's ten structural
contracts, exact resource admission, emitters, routes, and stock fallback stay
local to this repository.

## Install

After a renderer exists, place the production JAR in BlueMap's add-on pack
directory and restart the BlueMap JVM. Removal plus one restart restores stock
behavior; the add-on creates no custom world state.

Set `-Dbluemap.arsnouveau.disabled=true` to leave the exact profile inactive.

## Scope boundary

Live contents, activity overlays, particles, animation phase, and unsupported
states stay stock or deterministic-neutral unless the owner explicitly expands
scope.

No Ars Nouveau binary, source, class, asset, captured mesh, or gallery is
bundled in the add-on.
