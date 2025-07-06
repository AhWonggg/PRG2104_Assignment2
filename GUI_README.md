# Global Development Data Analysis Project - GUI Guide

## Project Overview
This project analyzes global development indicator data to answer three key questions:

1. **Which country had the highest life expectancy, and in which year?**
2. **Which country performed best in health & education?**
3. **Which country had the highest forest area loss from 2000 to 2020?**

## How to Run

### Method 1: Graphical Interface (Recommended)
```bash
sbt "runMain DevelopmentGUIApp"
```
Or double-click the `run-gui.bat` file

### Method 2: Command Line Interface
```bash
sbt "runMain MainApp"
```

### Method 3: Using Default Main Class
```bash
sbt run
```
(Now defaults to GUI version)

## GUI Features

### Interface Design
- **Modern Appearance**: Uses system native look and feel
- **Card-based Layout**: Each question displayed in individual cards
- **Responsive Design**: Resizable window
- **Status Indicator**: Shows data loading status

### Display Content
- **Data Status**: Shows the number of successfully loaded data records
- **Three Analysis Results**:
  - Question 1: Country and year with highest life expectancy
  - Question 2: Country with best comprehensive health & education performance
  - Question 3: Country with highest forest area loss and percentage

### Interface Elements
- **Title Bar**: Project name display
- **Status Bar**: Data loading confirmation
- **Result Cards**: Clear display of numbering, questions and answers
- **Footer**: Analysis completion confirmation

## Technical Features

### Data Processing
- **Robust CSV Parsing**: Handles quoted fields and null values
- **Smart Data Cleaning**: Filters invalid and missing data
- **Type Safety**: Uses appropriate data types

### Analysis Algorithms
- **Life Expectancy Analysis**: Finds historical maximum records
- **Comprehensive Scoring System**: Normalizes multiple health & education indicators
- **Geographic Filtering**: Excludes global and regional aggregate data

### GUI Architecture
- **Swing Framework**: Ensures cross-platform compatibility
- **Separation of Concerns**: Data logic separated from interface
- **Error Handling**: Gracefully handles data loading exceptions

## Project Structure
```
src/main/scala/
├── MainApp.scala           # Command line main program
├── DevelopmentGUI.scala    # Graphical interface program
├── Analysis.scala          # Data analysis logic
├── Loader.scala           # Data loader
├── CountryData.scala      # Data model
└── HeaderExtract/
    └── HeaderReader.scala  # Header extraction tool
```

## Output Example

### Command Line Output
```
1) Country with highest life expectancy: San Marino in 2012
2) Country with best health & education: France  
3) Country with highest forest loss: Paraguay lost 17.34%
```

### GUI Interface
- Beautiful graphical interface with all analysis results
- Modern card design
- Clear data status indicators

## Requirements
- Scala 3.3.6
- Java 17+
- SBT 1.x
- Swing (built into JDK)

---
*Project developed in Scala using SBT build tool. GUI based on Java Swing framework for excellent cross-platform compatibility.*
