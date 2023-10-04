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

enum class pieceWorth(val value: Int) {
	PAWN(1),
	PEASANT(2),
	SOLDIER(3),
	ROOK(9),
	KNIGHT(4),
	BISHOP(6),
	CATAPULT(3),
	CHAMBERLAIN(6),
	COURTESAN(6),
	HERALD(6),
	INQUISITOR(8),
	LANCER(5),
	PONTIFF(8),
	THIEF(5),
	TOWER(10),
	QUEEN(12),
	JESTER(12),
	KING(0),
	REGENT(15)
}

@SpringBootApplication
class FaerieChessCounterApplication

fun main(args: Array<String>) {
	runApplication<FaerieChessCounterApplication>(*args)
}

@Controller
@RequestMapping("/")
class ChessController {

	val beginnerPoints = 65
	val intermediatePoints = 70
	val advancedPoints = 75

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
		var totalPoints = if (queen == 0) pieceWorth.QUEEN.value else pieceWorth.JESTER.value
		totalPoints += if (king == 0) pieceWorth.KING.value else pieceWorth.REGENT.value
		totalPoints +=
				pawn * pieceWorth.PAWN.value +
				peasant * pieceWorth.PEASANT.value +
				soldier * pieceWorth.SOLDIER.value +
				rook * pieceWorth.ROOK.value +
				knight * pieceWorth.KNIGHT.value +
				bishop * pieceWorth.BISHOP.value +
				catapult * pieceWorth.CATAPULT.value +
				chamberlain * pieceWorth.CHAMBERLAIN.value +
				courtesan * pieceWorth.COURTESAN.value +
				herald * pieceWorth.HERALD.value +
				inquisitor * pieceWorth.INQUISITOR.value +
				lancer * pieceWorth.LANCER.value +
				pontiff * pieceWorth.PONTIFF.value +
				thief * pieceWorth.THIEF.value +
				tower * pieceWorth.TOWER.value

		val difficulties = mapOf(
			"Beginner" to beginnerPoints,
			"Intermediate" to intermediatePoints,
			"Advanced" to advancedPoints
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
