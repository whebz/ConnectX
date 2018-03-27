package forzaquattro.model;

/**
 * Game rules definition.
 */
public enum GameVariant {
    /**
     * Set game with standard rule. (H(6) x W(7)) board size.
     */
    Forza4,
    /**
     * Set game with 1st variant rule [connect 5 piece]. (H(7) x W(9)) board size.
     */
    Forza5,
    /**
     * Set game with standard rule. (H(8) x W(8)) board size.
     * resource: 
     * http://www.pergioco.net/Giochi/GiochiDiTavoliere/Forza4/Forza4.htm
     */
    Viviani,
    /**
     * Set game with standard rule. (H(6) x W(7)) board size.
     * resource:
     * http://www.genovagioca.it/Giochi/Recensioni/Forza4Varianti.aspx.
     */
    Attenta
}
