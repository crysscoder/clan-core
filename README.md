<div align="center">

# ClanCore

![Release](https://img.shields.io/github/v/release/crysscoder/clan-core?style=flat-square&label=release)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Paper](https://img.shields.io/badge/Paper-1.20.1-2ea44f?style=flat-square)
![Vault](https://img.shields.io/badge/Vault-required-6f42c1?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-required-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Issues](https://img.shields.io/github/issues/crysscoder/clan-core?style=flat-square)

Paper-плагин кланов с GUI, Vault и MySQL.

[Release](https://github.com/crysscoder/clan-core/releases/latest) · [Issues](https://github.com/crysscoder/clan-core/issues) · [CodeAdapter](https://codeadapter.ru)

</div>

## Что делает

- создаёт PvP/PvE кланы
- хранит данные кланов в MySQL
- добавляет казну, приглашения и GUI-меню
- работает с экономикой через Vault

## Версии

| Компонент | Версия |
| --- | --- |
| Plugin | `1.0.0` |
| Java | `17` |
| Paper API | `1.20.1-R0.1-SNAPSHOT` |
| Vault API | `1.7` |
| HikariCP | `5.1.0` |
| MySQL Connector | `8.0.33` |

## Команды

- `/clan`

Подкоманды: `accept`, `chat`, `delete`, `glow`, `home`, `invite`, `invest`, `kick`, `leave`, `list`, `member`, `money`, `newleader`, `sethome`, `take`, `withdraw`.

## Сборка

```powershell
.\gradlew.bat clean build
```

## Запуск тестового сервера

```powershell
.\gradlew.bat runServer
```

## Настройка

Нужны Vault, economy-плагин и MySQL. Данные базы лежат в `bd.yml`.
