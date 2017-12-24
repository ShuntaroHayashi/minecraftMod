@echo off
cd %~dp0
C:\Users\k014c1105\forge-1.7.10-10.13.4.1558-1.7.10-src\gradlew build
cd C:\Users\k014c1105
copy pleiades\workspace\ForestMoon\build\libs\EconomicsCraft.1.7.10-bata-1.2.0.jar Desktop\minecraftforge\mods\EconomicsCraft.1.7.10-bata-1.2.0.jar
copy pleiades\workspace\ForestMoon\build\libs\EconomicsCraft.1.7.10-bata-1.2.0.jar Desktop\forge\mods\EconomicsCraft.1.7.10-bata-1.2.0.jar
copy pleiades\workspace\ForestMoon\build\libs\EconomicsCraft.1.7.10-bata-1.2.0.jar Desktop\minecraftServer\mods\EconomicsCraft.1.7.10-bata-1.2.0.jar
pause