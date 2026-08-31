# BlueMap Ars Nouveau Add-on

A Java 21 BlueMap 5.23 feature-backport add-on for the exact
`ars-nouveau-5.13.0-mc1.21.1` profile in All the Mons `1.2.0` / Minecraft
`1.21.1`.

Version `0.1.0-alpha.3` carries the owner-accepted renderer to BlueMap's 5.23
feature backport. It targets only feature-backport commit
`7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` and API commit
`285c9a60eff3ac2b0cab308ce1058d1565be0971`. Eleven block-entity-rendered
hosts still use deterministic static meshes compiled from ten
operator-installed GEO resources and their installed textures.

## Build

Clone with `--recurse-submodules`, or initialize an existing checkout with
`git submodule update --init --recursive`. The settings preflight accepts only
the committed toolkit gitlink at
`6cd34a8368cc4ee8628fbe830a90ec5b14960629` and the Installed-GEO source
module at `c80a83eb6e2cb0bb05a69ace9716ef08b9db14f2`, with Java source tree
`8db87f933557d54c5ede2db70d94f67eaf44c30b`. It also pins Adapter API
`0.1.0-alpha.2` at commit `e81f08bc4bfbf02d810ec8949a019130e2e61634`
and source tree `2f974c9bb2ba13888d69682f86f30f58922d30eb`. It rejects an
uninitialized, changed, dirty, or mismatched checkout.

```bash
gradle --no-daemon -PbluemapSourcePath=../bluemap-backport clean check build
```

`check` is the Java, checkstyle, and archive gate. `prototypeCheck` also
requires the exact Ars Nouveau JAR and validates the 12-case gallery. See
`provenance/upstreams.json` for immutable artifact identities and
the [execution guide](docs/EXECUTION.md) for the prototype-to-release loop.

The build compiles the Installed-GEO module's three Java sources and the
Adapter API's four Java sources directly. Neither standalone module JAR is a
runtime dependency or nested in the add-on. Ars Nouveau's ten structural
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
