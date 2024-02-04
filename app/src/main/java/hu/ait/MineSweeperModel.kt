package hu.ait

object MineSweeperModel {

    private const val EMPTY: Int = -100
    const val BOMB: Int  =  100
    private const val rowNumber: Int = 5
    const val mineNumber: Int = 3
    var isExplode: Boolean = false

    val model = arrayOf(
        intArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
        intArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
        intArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
        intArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
        intArrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
    )

    fun getFieldContent(x: Int, y: Int) :Int {
        return model[x][y]
    }

    fun setFieldContent(x: Int, y: Int) {
        model[x][y] = countAroundMines(x, y, model)
    }

    // Function to generate random bomb locations and update the model
    private fun generateRandomBombs() {
        val bombPositions = mutableSetOf<Int>()

        while (bombPositions.size < mineNumber) {
            val randomPosition = (0 until (rowNumber * rowNumber)).random()
            if (bombPositions.add(randomPosition)) {
                val row = randomPosition / rowNumber
                val col = randomPosition % rowNumber
                model[row][col] = BOMB
            }
        }
    }

    init {
        generateRandomBombs()
    }

    /**
     * Calculate the number of bombs around a given cell
     *
     * @param x x coordinate of the cell
     * @param y y coordinate of the cell
     * @param model the model of the game
     * @return the number of bombs around the cell
     */

    fun countAroundMines(x: Int, y: Int, model: Array<IntArray>): Int {

        val directions = arrayOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1), Pair(0, 1),
            Pair(1, -1), Pair(1, 0), Pair(1, 1)
        )
        var count = 0

        for ((dx, dy) in directions) {
            val newX = x + dx
            val newY = y + dy

            if (newX in 0 until rowNumber && newY in 0 until rowNumber && model[newX][newY] == BOMB) {
                count++
            }
        }
        return count
    }

    /**
     * Reset the model to the initial state
     */
    fun resetModel() {
        for (i in 0..rowNumber) {
            for (j in 0..rowNumber) {
                model[i][j] = EMPTY
            }
        }
        generateRandomBombs()
    }

}