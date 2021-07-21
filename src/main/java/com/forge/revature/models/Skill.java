package com.forge.revature.models;

@Entity
@Table(name="skills")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Skill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String header;
	
	@Column
	private int value;
	
	@ManyToOne
    @JoinColumn(name = "matrix_id")
    private Matrix matrix;

	public Skill(String header, int value, Matrix matrix) {
		super();
		this.header = header;
		this.value = value;
		this.matrix = matrix;
	}
	
}
