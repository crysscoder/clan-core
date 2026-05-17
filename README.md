# ClanCore

Paper-плагин кланов с GUI, Vault и MySQL.

## Что делает

- Создаёт кланы PvP/PvE типа.
- Хранит данные кланов в MySQL.
- Добавляет казну, приглашения и GUI меню.
- Работает с экономикой через Vault.

## Зачем нужен

Подходит для survival, RPG и PvP-серверов, где нужна клановая система с экономикой и меню.

## Версии

- Java 17
- Gradle 8.8
- Paper API 1.20.1
- Shadow 8.1.1
- Lombok 1.18.34
- Vault API 1.7
- HikariCP 5.1.0
- MySQL Connector 8.0.33
- Plugin `1.0.0`

## Команда

- `/clan`

Подкоманды: `accept`, `chat`, `delete`, `glow`, `home`, `invite`, `invest`, `kick`, `leave`, `list`, `member`, `money`, `newleader`, `sethome`, `take`, `withdraw`.

## Сборка

```powershell
.\gradlew.bat clean build
```

Jar для сервера: `build/libs/ClanCore.jar`

## Запуск тестового сервера

```powershell
.\gradlew.bat runServer
```

## Настройка

Нужны Vault, economy-плагин и MySQL. Данные БД лежат в `bd.yml`.
