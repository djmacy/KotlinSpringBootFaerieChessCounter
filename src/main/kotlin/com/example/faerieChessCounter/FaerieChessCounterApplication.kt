package com.example.faerieChessCounter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@SpringBootApplication
class FaerieChessCounterApplication

fun main(args: Array<String>) {
	runApplication<FaerieChessCounterApplication>(*args)
}

@Controller
@RequestMapping("/")
class ChessController {

	@GetMapping("/")
	fun home(): String {
		return "redirect:/index.html"
	}

	@PostMapping("/calculate")
	fun calculate(
		@RequestParam pawn: Int,
		@RequestParam peasant: Int,
		@RequestParam soldier: Int,
		@RequestParam rook: Int,
		@RequestParam knight: Int,
		@RequestParam bishop: Int,
		@RequestParam catapult: Int,
		@RequestParam chamberlain: Int,
		@RequestParam courtesan: Int,
		@RequestParam herald: Int,
		@RequestParam inquisitor: Int,
		@RequestParam lancer: Int,
		@RequestParam pontiff: Int,
		@RequestParam thief: Int,
		@RequestParam tower: Int,
		@RequestParam queen: Int,
		@RequestParam king: Int,
		@RequestParam jester: Int,
		@RequestParam regent: Int,
		@RequestParam difficulty: String,
		model: Model
	): String {
		val totalPoints = pawn * 1 +
				peasant * 2 +
				soldier * 3 +
				rook * 9 +
				knight * 4 +
				bishop * 6 +
				catapult * 3 +
				chamberlain * 6 +
				courtesan * 6 +
				herald * 6 +
				inquisitor * 8 +
				lancer * 5 +
				pontiff * 8 +
				thief * 5 +
				tower * 10 +
				queen * 12 +
				king * 0 +
				jester * 12 +
				regent * 15 +
				regent * 15

		val difficulties = mapOf(
			"Beginner" to 65,
			"Intermediate" to 70,
			"Advanced" to 75
		)
		val remainingPoints = (difficulties[difficulty] ?: 0) - totalPoints
		//If null 0

		model.addAttribute("total_points", totalPoints)
		model.addAttribute("remaining_points", remainingPoints)

		return "result"
	}
}
