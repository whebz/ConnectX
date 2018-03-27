package forzaquattro.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import forzaquattro.model.GameType;

/**
 * Simple class to manage stats with JTables.
 * It is variable, if GameType is PvsP it shows a table, if PvsC another.
 */
public class StatsTableModel extends AbstractTableModel {

    /**
     * Generated Serial Version UID.
     */
    private static final long serialVersionUID = 434851830275214191L;
    private static final int COLUMNS_NUM = 5;
    private List<StatsTableLine> values;
    private GameType gt;

    /**
     * @param gt
     *              game type to change table dynamically
     * @param list
     *              the list of rows to show in the table
     */
    public StatsTableModel(final List<StatsTableLine> list, final GameType gt) {
        this.gt = gt;
        this.values = list;
    }
    @Override
    public int getRowCount() {
        return gt.equals(GameType.PvsP) ? 4 : 3;
    }

    @Override
    public int getColumnCount() {
        return COLUMNS_NUM;
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {

        StatsTableLine s = this.values.get(rowIndex);

        switch (columnIndex) {
        case 0:
            return s.getDifficult();
        case 1:
            return s.getWons();
        case 2:
            return s.getLosts();
        case 3: 
            return s.getDraws();
        case 4:
            return s.getPerc();
        default:
        }
       return 0;
    }


    @Override
    public String getColumnName(final int column) {

        switch (column) {
            case 0: return this.gt.equals(GameType.PvsC) ? "Difficult" : "Variant";
            case 1: return "Won";
            case 2: return "Lost";
            case 3: return "Draw";
            case 4: return "Won %";
            default:
        }

        return "";
    }

    @Override
    public Class<?> getColumnClass(final int column) {

        switch (column) {
            case 0: return String.class;
            case 1: return Integer.class;
            case 2: return Integer.class;
            case 3: return Integer.class;
            case 4: return Double.class;
            default:
        }
        return Object.class;
    }
}
