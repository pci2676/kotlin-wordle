package edu.nextstep.wordle.application.wordle

import edu.nextstep.wordle.application.wordle.dictionary.WordFinder

data class Wordle(
    val target: Word,
    val wordResult: List<WordResult> = emptyList(),
    val wordFinder: WordFinder,
) {
    val round = wordResult.size + 1

    fun input(word: Word): WordleAnswer {
        if (wordFinder.notContain(word)) {
            return WordleAnswer.Retry(this, "사전에 없는 단어($word)입니다.")
        }

        val result = this.target.match(word)
        val wordResult = this.wordResult + WordResult(round = this.round, windowResults = result)

        return WordleAnswer.Right(this.copy(wordResult = wordResult))
    }

    private fun WordFinder.notContain(word: Word): Boolean = !this.contain(word)

    fun isSuccess(): Boolean {
        return WordResults(this.wordResult)
            .isSuccess()
    }
}