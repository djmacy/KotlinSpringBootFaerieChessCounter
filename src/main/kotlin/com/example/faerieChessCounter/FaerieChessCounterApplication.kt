package com.example.faerieChessCounter

import jakarta.servlet.http.HttpSession
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
	fun home(model: Model, session: HttpSession): String {
		//as? will default the values to the SelectedPieces class if the values are null
		val selectedPieces = session.getAttribute("selectedPieces") as? SelectedPieces
		//add the selectedPieces attribute to the model with the user values or default if not found
		model.addAttribute("selectedPieces", selectedPieces ?: SelectedPieces())
		return "index"
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
		@RequestParam difficulty: String,
		session: HttpSession,
		model: Model
	): String {
		//technically it does not matter for queen and jester because they are worth the same
		var totalPoints = if (queen == 0) 12 else 12
		totalPoints += if (king == 0) 0 else 15
		totalPoints +=
				pawn * 1 +
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
				tower * 10


		val difficulties = mapOf(
			"Beginner" to 65,
			"Intermediate" to 70,
			"Advanced" to 75
		)
		//If null 0
		val remainingPoints = (difficulties[difficulty] ?: 0) - totalPoints

		model.addAttribute("total_points", totalPoints)
		model.addAttribute("remaining_points", remainingPoints)
		//store the selected pieces counts
		val selectedPieces = SelectedPieces(
			pawn, peasant, soldier, rook, knight, bishop, catapult, chamberlain,
			courtesan, herald, inquisitor, lancer, pontiff, thief, tower, queen, king,
		)

		session.setAttribute("selectedPieces", selectedPieces)
		return "result"
	}
}
