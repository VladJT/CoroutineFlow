package com.sumin.coroutineflow.team_score

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.max

class TeamScoreViewModel : ViewModel() {
    private val _state = MutableStateFlow<TeamScoreState>(TeamScoreState.Game(0, 0))
    val state = _state.asStateFlow()

    fun increaseScore(team: Team) {
        try {
            val currentState = _state.value
            if (currentState is TeamScoreState.Game) {
                var score1 = currentState.score1
                var score2 = currentState.score2
                if (team == Team.TEAM_1) {
                    score1++
                } else {
                    score2++
                }

                if (max(score1, score2) >= WINNER_SCORE) {
                    _state.value =
                        TeamScoreState.Winner(
                            score1 = score1,
                            score2 = score2,
                            winnerTeam = if (score1 > score2) Team.TEAM_1 else Team.TEAM_2
                        )

                } else {
                    _state.value = TeamScoreState.Game(score1, score2)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    companion object {
        private const val WINNER_SCORE = 7
    }
}
