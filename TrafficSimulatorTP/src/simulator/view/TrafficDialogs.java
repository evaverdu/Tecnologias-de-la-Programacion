package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.SimulatedObject;

public abstract class TrafficDialogs extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JList<String> object;
	private DefaultListModel<String> _objectModel;
	private JComboBox<Object> object2;
	protected String _name1;
	protected String _name2;
	protected JSpinner ticks;
	private int _status;
	protected String _info;
	protected Object[] options;
	
	public TrafficDialogs(Frame frame, String info, String title, String name1, String name2, Object [] options) {
		super(frame, true);
		this._info = info;
		this._name1 = name1;
		this._name2 = name2;
		this.options = options;
		this.setTitle(title);
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(80, 40));
		
		//Panel de arriba
		
		JLabel info = new JLabel(_info);

		//Panel central
		JPanel centerPanel = new JPanel(new FlowLayout());
		
		JLabel name1 =  new JLabel(_name1);
		JLabel name2 =  new JLabel(_name2);
		JLabel namet =  new JLabel(" Ticks: ");
		
		JPanel panel = new JPanel();
		_objectModel = new DefaultListModel<>();
		object = new JList<String>(_objectModel); 
		object.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		object.setLayoutOrientation(JList.VERTICAL);
		object.setFixedCellWidth(15);
		object.setFixedCellHeight(15);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(object);
		panel.add(scrollPane);
		
		object2 = new JComboBox<Object>(options);
		
		ticks = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
		
		centerPanel.add(name1);
		centerPanel.add(scrollPane);
		centerPanel.add(name2);
		centerPanel.add(object2);
		centerPanel.add(namet);
		centerPanel.add(ticks);
		
		//Panel de abajo
		JPanel surPanel = new JPanel(new FlowLayout());
		
		JButton cancelbtn = new JButton("Cancel");
		cancelbtn.setPreferredSize(new Dimension(80, 30));
		cancelbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				TrafficDialogs.this.setVisible(false);
			}
		});
		JButton okbtn = new JButton("OK");
		okbtn.setPreferredSize(new Dimension(80, 30));
		okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (object.getListSelectionListeners() != null && object2.getSelectedItem() != null && ticks.getComponentCount() != 0) {
					_status = 1;
					TrafficDialogs.this.setVisible(false);
				}
			}
		});
		surPanel.add(cancelbtn);
		surPanel.add(okbtn);
		
		//Main Panel
		mainPanel.add(info, BorderLayout.NORTH);
		mainPanel.add(centerPanel,  BorderLayout.CENTER);
		mainPanel.add(surPanel,  BorderLayout.SOUTH);
		
		this.add(mainPanel);
	    this.setVisible(false); 
	    this.pack();
	    
	}
	
	public <T> int open(List<T> list) {
		_objectModel.removeAllElements();
		for (int i = 0; i < list.size(); i++) {
			_objectModel.addElement(((SimulatedObject) list.get(i)).getId());
		}
		setVisible(true);	
		return _status;
	}
	
	public Object getObject2() {
		return (Object)object2.getSelectedItem();
	}
	
	public JList<String> getObject() {
		return object;
	}
	
	public int getTicks() {
		return (int)ticks.getValue();	
	}
}
