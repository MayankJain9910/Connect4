# 🎮 Connect 4 Game

A simple two-player **Connect 4** game built using Java and JavaFX. This classic game is all about strategy — the first player to form a horizontal, vertical, or diagonal line of four discs wins!

## 🧠 Game Rules

* Two players take turns dropping colored discs into a 7-column, 6-row vertically suspended grid.
* The pieces fall straight down, occupying the lowest available space within the column.
* The first player to connect four of their discs vertically, horizontally, or diagonally wins.
* If the board fills up before anyone wins, it's a draw.

---

## 💻 Technologies Used

* Java 21
* JavaFX 21
* FXML (UI Layout)
* Maven (for dependency management)
* Eclipse IDE (Recommended for development)

---

## 📁 Project Structure

```
Connect4/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/internshala/connect4/   # Java source code
│       └── resources/
│           └── com/internshala/connect4/
│               └── main_view.fxml          # FXML UI layout
└── pom.xml                                 # Maven config
    README.md
```

---

## 🚀 How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/connect4.git
   cd connect4
   ```

2. Make sure you have **Java 21** and **Maven** installed.

3. In Eclipse:

   * Import the project as a Maven project.
   * Right-click the main class (e.g., `Main.java`) and choose `Run As → Java Application`.

4. The game window will launch and you can start playing!
---

## 🛆 Dependencies

* `org.openjfx:javafx-controls`
* `org.openjfx:javafx-fxml`

Make sure the appropriate JavaFX SDK is configured in your Maven `pom.xml` and module path.

---

## **🙇‍♂️ Developer**

**Mayank Jain** 📧 [mayankjain9910@gmail.com](mailto:mayankjain9910@gmail.com) 🌐 [GitHub - MayankJain9910](https://github.com/MayankJain9910)
