# Ars Nouveau renderer gallery

This bounded fixture places the 11 Geo-rendered blocks selected for the first
Ars Nouveau add-on pass. A `minecraft:stone` block at `(200, 101, 200)` is the
stock-rendering control. Each block has a glowing sign with its name and exact
coordinates.

```bash
python gallery/generate.py
python gallery/generate.py --check
python gallery/lint.py
bash gallery/package.sh /tmp/ars_nouveau-gallery.zip
```

## In-game setup

Install the generated data pack in the disposable test world, reload it, and
run:

```text
/function ars_nouveau_gallery:build
/function ars_nouveau_gallery:tp
```

The rows contain apparatuses at `z=176`, relays at `z=184`, spell turrets at
`z=192`, and the stone control at `z=200`. `placements.tsv` is the exact review
manifest. `/function ars_nouveau_gallery:release` removes the fixture.
