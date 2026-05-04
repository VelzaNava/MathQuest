package com.mathquest.app.data

data class Problem(
    val question: String,
    val answer: String,
    val hint: String
)

data class LessonExample(
    val label: String,
    val visual: String,
    val equation: String
)

data class Lesson(
    val title: String,
    val body: String,
    val examples: List<LessonExample>
)

data class Chapter(
    val id: Int,
    val title: String,
    val monsterName: String,
    val emoji: String,
    val topic: String,
    val lesson: Lesson,
    val problems: List<Problem>
)

data class CutscenePanel(
    val art: String,
    val text: String
)

val CUTSCENES = listOf(
    CutscenePanel(
        art = "📖",
        text = "One quiet afternoon, {playerName} found a dusty book on the shelf. The title glowed with golden light — \"Math Quest.\" They reached out and opened it..."
    ),
    CutscenePanel(
        art = "🌀",
        text = "Whoooosh! The pages swirled like a portal and you were pulled inside! Colors, numbers, and stars rushed past you in every direction..."
    ),
    CutscenePanel(
        art = "🧙",
        text = "You landed in a magical world. A tiny glowing guide floated up to greet you.\n\n\"Welcome to Wumbo! I have been waiting for you.\""
    ),
    CutscenePanel(
        art = "🌟",
        text = "\"{playerName}, three monsters have stolen the power of numbers from Wumbo! To restore peace, you must defeat them — by solving their math challenges.\""
    ),
    CutscenePanel(
        art = "⚔️",
        text = "\"Are you ready, brave one? The Forest of Many awaits. Three monsters. Three victories. One hero — you!\"\n\nLet the quest begin! 🌟"
    )
)

val CHAPTERS = listOf(
    Chapter(
        id = 0,
        title = "Forest of Many",
        monsterName = "Multiplico",
        emoji = "🌲",
        topic = "× Multiplication",
        lesson = Lesson(
            title = "What is Multiplication?",
            body = "Multiplication is adding the same number over and over! If you have 3 bags with 4 apples each, instead of 4+4+4, just write 3×4=12. It is a shortcut for repeated addition!",
            examples = listOf(
                LessonExample(
                    label = "3 groups of 4 apples",
                    visual = "🍎🍎🍎🍎   🍎🍎🍎🍎   🍎🍎🍎🍎",
                    equation = "3 × 4 = 12"
                ),
                LessonExample(
                    label = "2 groups of 5 stars",
                    visual = "⭐⭐⭐⭐⭐   ⭐⭐⭐⭐⭐",
                    equation = "2 × 5 = 10"
                )
            )
        ),
        problems = listOf(
            Problem(
                question = "The Forest Monster placed 4 enchanted acorns in each of 5 trees. How many acorns are there in total?",
                answer = "20",
                hint = "5 trees × 4 acorns each. What is 5 × 4?"
            ),
            Problem(
                question = "A magical butterfly has 6 wings and there are 6 butterflies in the forest. How many wings are there in total?",
                answer = "36",
                hint = "6 butterflies × 6 wings each. What is 6 × 6?"
            ),
            Problem(
                question = "A wizard planted 7 rows of mushrooms with 8 mushrooms in each row. How many mushrooms did he plant?",
                answer = "56",
                hint = "7 rows × 8 mushrooms per row. What is 7 × 8?"
            ),
            Problem(
                question = "Each potion bottle holds 9 drops of magic liquid. There are 4 bottles. How many drops are there in total?",
                answer = "36",
                hint = "4 bottles × 9 drops each. What is 4 × 9?"
            ),
            Problem(
                question = "A fairy collected 6 flowers every hour for 5 hours. How many flowers did she collect in total?",
                answer = "30",
                hint = "5 hours × 6 flowers per hour. What is 5 × 6?"
            )
        )
    ),
    Chapter(
        id = 1,
        title = "Castle of Splitting",
        monsterName = "Divido",
        emoji = "🏰",
        topic = "÷ Division",
        lesson = Lesson(
            title = "What is Division?",
            body = "Division means splitting things into equal groups! If you have 12 cookies and want to share them equally among 3 friends, you divide: 12 divided by 3 equals 4. Each friend gets 4 cookies!",
            examples = listOf(
                LessonExample(
                    label = "12 shared among 3 friends",
                    visual = "🍪🍪🍪🍪   🍪🍪🍪🍪   🍪🍪🍪🍪",
                    equation = "12 ÷ 3 = 4"
                ),
                LessonExample(
                    label = "10 shared among 2",
                    visual = "⚡⚡⚡⚡⚡   ⚡⚡⚡⚡⚡",
                    equation = "10 ÷ 2 = 5"
                )
            )
        ),
        problems = listOf(
            Problem(
                question = "Divido hoarded 24 spell books and locked them in 4 equal towers. How many books are in each tower?",
                answer = "6",
                hint = "24 books divided into 4 towers. What is 24 ÷ 4?"
            ),
            Problem(
                question = "A dragon has 35 gold coins and wants to split them equally into 5 treasure chests. How many coins go in each chest?",
                answer = "7",
                hint = "35 coins divided into 5 chests. What is 35 ÷ 5?"
            ),
            Problem(
                question = "There are 48 knights that need to be split equally into 6 battalions. How many knights are in each battalion?",
                answer = "8",
                hint = "48 knights divided into 6 groups. What is 48 ÷ 6?"
            ),
            Problem(
                question = "A witch made 32 potions and stored them equally in 8 rows on a shelf. How many potions are in each row?",
                answer = "4",
                hint = "32 potions divided into 8 rows. What is 32 ÷ 8?"
            ),
            Problem(
                question = "The royal baker baked 54 bread loaves to be shared equally among 9 villages. How many loaves does each village get?",
                answer = "6",
                hint = "54 loaves divided among 9 villages. What is 54 ÷ 9?"
            )
        )
    ),
    Chapter(
        id = 2,
        title = "Deep Ocean Rift",
        monsterName = "Fractura",
        emoji = "🌊",
        topic = "½ Fractions",
        lesson = Lesson(
            title = "What is a Fraction?",
            body = "A fraction shows a part of a whole! If you cut a pizza into 4 equal slices and eat 1, you ate 1/4 of the pizza. The bottom number is the total parts. The top number is your part!",
            examples = listOf(
                LessonExample(
                    label = "1 out of 4 pizza slices",
                    visual = "🍕🍕🍕🍕  →  ate 1 piece",
                    equation = "= 1/4 of the pizza"
                ),
                LessonExample(
                    label = "2 out of 3 are blue",
                    visual = "🔵🔵⚪",
                    equation = "= 2/3 are blue"
                )
            )
        ),
        problems = listOf(
            Problem(
                question = "A sea cake was cut into 8 equal pieces. Fractura ate 3 pieces. What fraction of the cake did Fractura eat? Write your answer as x/y.",
                answer = "3/8",
                hint = "Pieces eaten over total pieces. 3 out of 8 = 3/?"
            ),
            Problem(
                question = "There are 5 mermaids. 2 of them have golden tails. What fraction of the mermaids have golden tails? Write as x/y.",
                answer = "2/5",
                hint = "Golden tails over total mermaids. 2 out of 5 = ?/5"
            ),
            Problem(
                question = "A treasure chest has 10 gems. 7 of them are blue sapphires. What fraction are sapphires? Write as x/y.",
                answer = "7/10",
                hint = "Sapphires over total gems. 7 out of 10 = 7/?"
            ),
            Problem(
                question = "An octopus has 8 arms. It painted 5 of its arms with stripes. What fraction of its arms have stripes? Write as x/y.",
                answer = "5/8",
                hint = "Striped arms over total arms. 5 out of 8 = ?/8"
            ),
            Problem(
                question = "There are 6 fish in a bowl. 4 of them are clownfish. What fraction are clownfish? Write as x/y.",
                answer = "4/6",
                hint = "Clownfish over total fish. 4 out of 6 = 4/?"
            )
        )
    )
)

val CORRECT_MESSAGES = listOf(
    "✅ Correct! Amazing!",
    "🌟 That's right! You're brilliant!",
    "⭐ Perfect! The monster weakens!",
    "🎉 Fantastic! Keep going!",
    "💫 Wow! You're a math hero!"
)

val WRONG_MESSAGES = listOf(
    "❌ Not quite! Try again!",
    "💔 Oops! You can do it!",
    "❌ So close! Think harder!",
    "💔 Almost! Don't give up!"
)
