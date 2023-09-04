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
}
