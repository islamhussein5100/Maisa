package com.example

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

object AssistantCore {

    fun processQuery(context: Context, query: String): String {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return "I didn't catch that. What would you like help with?"
        val lower = trimmed.lowercase(Locale.getDefault())

        return when {
            // Greetings
            lower.matches(Regex(".*\\b(hi|hello|hey|greetings|hola|good morning|good afternoon|good evening)\\b.*")) -> {
                val hour = SimpleDateFormat("H", Locale.getDefault()).format(Date()).toIntOrNull() ?: 12
                val greeting = when (hour) {
                    in 5..11 -> "Good morning!"
                    in 12..16 -> "Good afternoon!"
                    in 17..21 -> "Good evening!"
                    else -> "Hello!"
                }
                "$greeting I'm Maya, your personal assistant. How can I assist you right now?"
            }

            // Identity & Introduction
            lower.contains("who are you") || lower.contains("your name") || lower.contains("what are you") -> {
                "I am Maya, your smart, responsive personal assistant on Android. I can answer questions, perform calculations, tell the time, share facts, and help you stay productive!"
            }

            // Capabilities & Help
            lower.contains("help") || lower.contains("what can you do") || lower.contains("capabilities") || lower.contains("features") -> {
                """
                Here are some things I can do for you:
                • Chat & answer general questions
                • Math & calculations (e.g. 'calculate 25 * 14' or 'what is 15% of 240')
                • Real-time info (e.g. 'what time is it', 'what is today's date')
                • Device status (e.g. 'battery level', 'battery status')
                • Productivity tips ('give me a productivity tip')
                • Fun facts & trivia ('tell me a fun fact')
                • Jokes & entertainment ('tell me a joke')
                • Voice support: tap the mic to speak or the speaker icon to listen!
                """.trimIndent()
            }

            // Time and Date
            lower.contains("time") -> {
                val timeFormat = SimpleDateFormat("h:mm a (zzzz)", Locale.getDefault())
                "The current time is ${timeFormat.format(Date())}."
            }
            lower.contains("date") || lower.contains("today") || lower.contains("day is it") -> {
                val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
                "Today is ${dateFormat.format(Date())}."
            }

            // Battery & Device Status
            lower.contains("battery") -> {
                getBatteryStatus(context)
            }

            // Math and Calculations
            lower.contains("calculate") || lower.contains("math") || lower.matches(Regex(".*\\d+\\s*[+\\-*/%^xX]\\s*\\d+.*")) -> {
                solveMath(trimmed)
            }

            // Productivity Tips
            lower.contains("productivity") || lower.contains("tip") || lower.contains("focus") || lower.contains("advice") -> {
                val tips = listOf(
                    "Try the 'Two-Minute Rule': If a task takes less than two minutes, do it immediately instead of putting it off.",
                    "Use the Pomodoro Technique: Work with complete focus for 25 minutes, then take a 5-minute restorative break.",
                    "Eat the Frog: Tackle your highest-priority and most demanding task first thing in the morning when your energy is highest.",
                    "Time Blocking: Schedule dedicated blocks in your day for deep focus work, and batch message-checking into designated windows.",
                    "Digital Declutter: Clear your notification bar and keep only essential alert permissions active to safeguard your focus."
                )
                "Productivity Tip: ${tips[Random.nextInt(tips.size)]}"
            }

            // Jokes
            lower.contains("joke") || lower.contains("funny") || lower.contains("laugh") -> {
                val jokes = listOf(
                    "Why do programmers prefer dark mode? Because light attracts bugs!",
                    "Why did the developer go broke? Because they used up all their cache!",
                    "There are 10 types of people in the world: those who understand binary, and those who don't.",
                    "Why was the cell phone wearing glasses? It had lost all its contacts!",
                    "How do trees access the internet? They log in!",
                    "Why do Java developers wear glasses? Because they don't C#!"
                )
                jokes[Random.nextInt(jokes.size)]
            }

            // Fun Facts & Trivia
            lower.contains("fact") || lower.contains("trivia") -> {
                val facts = listOf(
                    "Honey never spoils. Archaeologists have excavated 3,000-year-old honey from Egyptian tombs that is still completely edible!",
                    "A single lightning bolt packs enough energy to toast 100,000 slices of bread.",
                    "Octopuses have three hearts, nine brains, and their blood is blue due to copper-based hemocyanin.",
                    "The first computer programmer was Ada Lovelace, who wrote an algorithm for Charles Babbage's Analytical Engine in 1843.",
                    "Sound travels about 4.3 times faster in water than in air!"
                )
                "Here's an interesting fact: ${facts[Random.nextInt(facts.size)]}"
            }

            // Motivation & Inspirational Quotes
            lower.contains("motivat") || lower.contains("quote") || lower.contains("inspire") -> {
                val quotes = listOf(
                    "“The secret of getting ahead is getting started.” — Mark Twain",
                    "“Simplicity is the soul of efficiency.” — Austin Freeman",
                    "“Action is the foundational key to all success.” — Pablo Picasso",
                    "“It always seems impossible until it's done.” — Nelson Mandela",
                    "“The best way to predict the future is to create it.” — Peter Drucker"
                )
                quotes[Random.nextInt(quotes.size)]
            }

            // Gratitude / Polite
            lower.contains("thank") || lower.contains("thanks") -> {
                "You're very welcome! I'm always here if you need anything else."
            }

            // Weather query
            lower.contains("weather") -> {
                "To get precise local weather, enable location services. Generally, make sure to stay hydrated, dressed comfortably for the season, and keep an umbrella handy if clouds are gathering!"
            }

            // How are you
            lower.contains("how are you") || lower.contains("how're you") -> {
                "I'm operating at peak performance and ready to assist! How are you doing today?"
            }

            // Default intelligent response
            else -> {
                "I hear you! Regarding \"$trimmed\": I'm actively learning new skills. You can ask me to do calculations, check the time/date, give productivity tips, tell jokes, or test device status!"
            }
        }
    }

    private fun getBatteryStatus(context: Context): String {
        return try {
            val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, intentFilter)
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL

            if (level >= 0 && scale > 0) {
                val batteryPct = (level * 100) / scale
                val chargingText = if (isCharging) "and currently charging ⚡" else "and not charging"
                "Your device battery is at $batteryPct% $chargingText."
            } else {
                "Battery information is currently unavailable."
            }
        } catch (e: Exception) {
            "Unable to read battery level right now."
        }
    }

    private fun solveMath(text: String): String {
        try {
            // Percentage check: "what is X% of Y" or "X percent of Y"
            val percentMatch = Regex("(\\d+(?:\\.\\d+)?)\\s*(?:%|percent)\\s+of\\s+(\\d+(?:\\.\\d+)?)", RegexOption.IGNORE_CASE).find(text)
            if (percentMatch != null) {
                val pct = percentMatch.groupValues[1].toDouble()
                val total = percentMatch.groupValues[2].toDouble()
                val result = (pct / 100.0) * total
                val formatted = if (result % 1.0 == 0.0) result.toLong().toString() else "%.2f".format(result)
                return "$pct% of $total is $formatted"
            }

            // Simple binary expression: num operator num
            val match = Regex("(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/xX^])\\s*(-?\\d+(?:\\.\\d+)?)").find(text)
            if (match != null) {
                val num1 = match.groupValues[1].toDouble()
                val op = match.groupValues[2]
                val num2 = match.groupValues[3].toDouble()

                val result = when (op) {
                    "+" -> num1 + num2
                    "-" -> num1 - num2
                    "*", "x", "X" -> num1 * num2
                    "/" -> {
                        if (num2 == 0.0) return "Division by zero is undefined!"
                        num1 / num2
                    }
                    "^" -> Math.pow(num1, num2)
                    else -> null
                }

                if (result != null) {
                    val formatted = if (result % 1.0 == 0.0) result.toLong().toString() else "%.4f".format(result).trimEnd('0').trimEnd('.')
                    return "$num1 $op $num2 = $formatted"
                }
            }
        } catch (e: Exception) {
            // fallthrough
        }
        return "I can calculate simple expressions like '25 * 4', '100 / 5', or '15% of 80'. Give it a try!"
    }
}
