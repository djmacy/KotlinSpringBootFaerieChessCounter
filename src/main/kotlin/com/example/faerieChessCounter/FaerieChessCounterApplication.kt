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
	val pawnValue = 1
	val peasantValue = 2
	val soldierValue = 3
	val rookValue = 9
	val bishopValue = 6
	val knightValue = 4
	val catapultValue = 3
	val courtesanValue = 6
	val chamberlainValue = 6
	val heraldValue = 6
	val inquisitorValue = 8
	val lancerValue = 5
	val pontiffValue = 8
	val thiefValue = 5
	val towerValue = 10
	val queenValue = 12
	val kingValue = 0
	val jesterValue = 12
	val regentValue = 15

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
		var totalPoints = if (queen == 0) queenValue else jesterValue
		totalPoints += if (king == 0) kingValue else regentValue
		totalPoints +=
				pawn * pawnValue +
				peasant * peasantValue +
				soldier * soldierValue +
				rook * rookValue +
				knight * knightValue +
				bishop * bishopValue +
				catapult * catapultValue +
				chamberlain * chamberlainValue +
				courtesan * courtesanValue +
				herald * heraldValue +
				inquisitor * inquisitorValue +
				lancer * lancerValue +
				pontiff * pontiffValue +
				thief * thiefValue +
				tower * towerValue

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
