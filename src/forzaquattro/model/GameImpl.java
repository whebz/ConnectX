package forzaquattro.model;

import java.util.List;

import forzaquattro.utils.MoveException;

/**
 * this model holds game logic and data.
 */
public class GameImpl implements Game {
    private Board board;
    private GameType type;
    private GameVariant variant;

    /**
     * Game Constructor for connect 4 variant.
     * @param gametype - define if Human vs Human or Human vs Computer.
     * @param gvariant - set different rule than standard base on game variant.
     */
    public GameImpl(final GameType gametype, final GameVariant gvariant) {
        this.board = new BoardImpl(gvariant);
        this.type = gametype;
        this.variant = gvariant;
    }

    /**
     * get the game type.
     * @return - GameType [player vs player or player vs computer]
     */
    public GameType getCurrentGameType() {
        return this.type;
    }

    /**
     * Get the current game board state.
     * @return - Board.
     */
    public Board getCurrentBoard() {
        return this.board;
    }
    /**
     * {@inheritDoc}
     */
    public BoardState getCurrentGameState() {
        BoardState state = board.getState(0);
        return state;
    }

    /**
     * {@inheritDoc}
     */
    public Move addpiece(final int col, final PieceColor piece) throws MoveException {
        Move move = this.board.droppiece(col, piece);
        if (this.variant == GameVariant.Attenta) {
            Move newmove = attentaVariantCheck();
            if (newmove != null) {
                this.board.undoLastMove();
                return this.board.droppiece(newmove.getColIndex(), piece);
            }
        }
        return move;
    }

    /**
     * @return - List of winning moves.
     */
    public List<Move> winningPiecesPositions() {
        return this.board.winningPositions();
    }

    /**
     * @return - new int [] { boardRowSize, boardColumnSize }.
     */
    public int[] boardSize() {
        return this.board.size();
    }

    private Move attentaVariantCheck() {
        VariantAttenta attenta = new VariantAttenta(this.board);
        return attenta.validateLastMove();
    }
}
