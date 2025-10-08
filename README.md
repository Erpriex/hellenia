# Hellenia
Discord bot built with JDA

## Requirements
This project requires the following to get started:
- Java version `17` or higher
- Maven version `3.9.11` or higher

## Getting Started

### Clone the repository
```bash
git clone https://github.com/Erpriex/hellenia.git
cd hellenia/
```

### Setup
1. Go to `src/main/resources`
2. Copy `config-sample.json` and rename it to `config.json`
3. Copy `hibernate-sample.cfg.xml` and rename it to `hibernate.cfg.xml`
4. Update the values in `config.json` and `hibernate.cfg.xml` to match your environment

### Compilation
```bash
mvn clean package
```

### Running
```bash
java -jar target/Hellenia-1.0-SNAPSHOT-jar-with-dependencies.jar
```