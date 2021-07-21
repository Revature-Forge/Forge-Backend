package com.forge.revature.models;

@Entity
@Table(name = "matrices")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Matrix {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column
	private String header;
	
	@ManyToOne
	@JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

	public Matrix(String header, Portfolio portfolio) {
		super();
		this.header = header;
		this.portfolio = portfolio;
	}
}