# 更新日志 / ChangeLog
## 2026-02-22 (v0.1.5-1.21.1-beta.2)
- 新增 `下界合金矿脉`（`netherite_mineral_vein_block`），并限制为仅在下界自然生成
- 新增矿机配方：`下界合金矿脉 -> 远古残骸`
- 调整矿机产速：`远古残骸` 统一为 `30s/个`（`600 tick`）
- 下界合金矿脉贴图已调整为与现有矿脉一致的资源路径与风格
- CI 发布流程更新：推送 `v*` 标签后自动构建并上传 jar 到 GitHub Release

- Added `Netherite Mineral Vein` (`netherite_mineral_vein_block`) and limited its worldgen to the Nether only
- Added rig recipe: `netherite_mineral_vein_block -> ancient_debris`
- Updated rig production speed for `ancient_debris` to `30s each` (`600 ticks`)
- Updated Netherite vein texture path/style to match existing vein assets
- Updated CI release flow: pushing `v*` tags now builds and uploads jar files to GitHub Release assets

## 2025-11-30
- 修复矿石的战利品表
- Fixed ore loot tables

## 2025-11-23
- 基本内容移植完成
- Basic content porting completed

## 2025-11-15
- `NeoForge`版移植工作开始
- Start porting to `NeoForge` version
