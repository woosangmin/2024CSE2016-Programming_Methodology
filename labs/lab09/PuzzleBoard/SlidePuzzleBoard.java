/* By the grace of the Lord */

package PuzzleBoard;

/** SlidePuzzleBoard - 퍼즐 보드를 만드는 모델 클래스 */
public class SlidePuzzleBoard {
    /** 보드의 행과 열의 크기 */
    private int board_size;
    /** 현재 비어 있는 조각의 열(row) */
    private int empty_row;
    /** 현재 비어 있는 조각의 행(col) */
    private int empty_col;
    /** 퍼즐 보드*/
    private PuzzlePiece[][] board;

    /** SlidePuzzleBoard - 생성자 */
    public SlidePuzzleBoard() {
        // 보드의 크기 초기화
        board_size = 4;
        // 퍼즐에 채워 넣을 액면 값 초기화
        int number = (board_size * board_size) - 1;
        // 보드를 크기에 맞춰 초기화
        board = new PuzzlePiece[board_size][board_size];
        // board[0][0]은 비어 있다.
        empty_row = 3;
        empty_col = 3;
        /* loop invariant : 최초 보드는 비어 있다.
        * 루프 종료 시 number의 값은 0이 되고 보드가 완성된다.
        * board[0][0]은 비어있고 그 다음부터 내림차순으로 채워지는 형태이다. */
        for (int i = 0 ; i < board_size ; i = i + 1) {
            /* loop invariant : 0번째 행부터 board_size-1번째 행까지 보드에 퍼즐을 채운다.*/
            for (int j = 0 ; j < board_size ; j = j + 1) {
                /* loop invariant : j번째 행의 0번째 열부터 board_size-1번째 열까지 number의 값으로 퍼즐을 채운다.
                * 루프가 반복될 때마다 number의 값은 1씩 줄어든다. */
                if (i == 3 && j == 3) {
                    board[i][j] = new PuzzlePiece(0);
                }
                else {
                    board[i][j] = new PuzzlePiece(number);
                    number = number - 1;
                }
            }
        }
    }

    /** getContents - 퍼즐 보드를 리턴한다. */
    public PuzzlePiece[][] getContents() {
        return board;
    }

    /** win - 퍼즐 보드를 다 풀었는지 확인한다. */
    public boolean win() {
        // count는 1 ~ (board_size * board_size) - 1까지 증가 
        int count = 1;
        // 반환 값
        boolean result = true;
        for (int i = 0 ; i < board_size && result ; i++) {
            for (int j = 0 ; j < board_size ; j++) {
                // 칸이 비어있지 않은 경우
                if (i != empty_row && j != empty_col) {
                    // 칸의 값이 count의 값과 다르면 result를 false로 바꾸고 루프를 멈춘다.
                    // 보드를 풀었을 경우 비어 있는 칸을 제외하고 1부터 오름차순으로 채워져 있을 것이다.
                    if (board[j][i].getFacevalue() != count) {
                        result = false;
                        break;
                    } else {
                        // 칸의 값이 count의 값과 같으면 카운트를 1씩 증가시킨다.
                        count++;
                    }
                }
            }
        }
        return result;
    }

    /** found - board[row][col]에 퍼즐 조각 v가 있는지 확인하는 함수
     * @param v - 찾고자 하는 값
     * @param row - 찾고자 하는 값의 예상 위치(행)
     * @param col - 찾고자 하는 값의 예상 위치(열)
     * @return result - 값이
     */
    public boolean found(int v, int row, int col) {
        boolean result = false;
        // 인자로 받은 행 또는 열의 위치가 보드 밖에 위치 하면 예상된 위치에 존재 하지 않는다.
        if (row < 0 || row >= board_size || col < 0 || col >= board_size)
            return result;
        else {
            // 인자로 받은 행 또는 열의 위치가 보드 안에 존재 하고 해당 칸의 값이 v이면 예상된 위치에 존재한다.
            if (board[row][col].getFacevalue() == v)
                result = true;
            return result;
        }
    }

    /** move - found 메소드를 사용하여 현재 퍼즐 보드에서 빈칸의 주변(상하좌우)에
     * 움직이려는 조각이 있는지 확인하고 이동을 시도한다. 이동에 성공한 경우에 true를
     * 리턴하고 이동이 불가능하다면 false를 리턴한다.
     * @param w - 옮기고자 하는 퍼즐의 액면 값
     * @return result - 성공 여부 */
    public boolean move(int w) {
        boolean result = false;
        if (found(w, empty_row-1, empty_col)) {
            /* 옮기고자 하는 퍼즐이 비어있는 칸의 위에 있는 경우
            * 값을 서로 바꿔 주고 empty_row의 값을 1 줄인 후
            * 성공했다고 체크한다. */
            board[empty_row][empty_col].setFacevalue(w);
            board[empty_row-1][empty_col].setFacevalue(0);
            empty_row -= 1;
            result = true;
        } else if (found(w, empty_row+1, empty_col)) {
            /* 옮기고자 하는 퍼즐이 비어있는 칸의 아래에 있는 경우
             * 값을 서로 바꿔 주고 empty_row의 값을 1 줄인 후
             * 성공했다고 체크한다. */
            board[empty_row][empty_col].setFacevalue(w);
            board[empty_row+1][empty_col].setFacevalue(0);
            empty_row += 1;
            result = true;
        } else if (found(w, empty_row, empty_col-1)) {
            /* 옮기고자 하는 퍼즐이 비어있는 칸의 왼쪽에 있는 경우
             * 값을 서로 바꿔 주고 empty_row의 값을 1 줄인 후
             * 성공했다고 체크한다. */
            board[empty_row][empty_col].setFacevalue(w);
            board[empty_row][empty_col-1].setFacevalue(0);
            empty_col -= 1;
            result = true;
        } else if (found(w, empty_row, empty_col+1)) {
            /* 옮기고자 하는 퍼즐이 비어있는 칸에 오른쪽에 있는 경우
             * 값을 서로 바꿔 주고 empty_row의 값을 1 줄인 후
             * 성공했다고 체크한다. */
            board[empty_row][empty_col].setFacevalue(w);
            board[empty_row][empty_col+1].setFacevalue(0);
            empty_col += 1;
            result = true;
        }
        return result;
    }
    /** getBoardsize() - 보드의 크기를 반환하는 메소드 */
    public int getBoardsize() {
        return board_size;
    }
}
