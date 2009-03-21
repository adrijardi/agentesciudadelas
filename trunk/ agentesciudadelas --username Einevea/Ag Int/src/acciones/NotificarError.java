package acciones;

import jade.content.AgentAction;

public class NotificarError implements AgentAction {

	private Error error;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}
