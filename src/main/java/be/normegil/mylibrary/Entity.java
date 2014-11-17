package be.normegil.mylibrary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

@JsonAutoDetect(
		fieldVisibility = JsonAutoDetect.Visibility.ANY,
		getterVisibility = JsonAutoDetect.Visibility.NONE,
		setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class Entity {

	private UUID id;

	public UUID getId() {
		return id;
	}

	public Entity() {
		this.id = UUID.randomUUID();
	 }
}
