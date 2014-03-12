package org.blackbananacoin.premature;

import java.util.Map;

import org.squirrelframework.foundation.component.SquirrelProvider;
import org.squirrelframework.foundation.fsm.DotVisitor;
import org.squirrelframework.foundation.fsm.ImmutableState;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
import org.squirrelframework.foundation.util.TypeReference;

public class SquirrelQuickStart {

	// 1. Define State Machine Event
	enum FSMEvent {
		StartUp, Download, ToC, Connected, ConnectionClosed, LoadSuccess, LoadFail, ConnectionLost, Shutdown, ConnectionRestored
	}

	enum FSMState {
		Idle, Loading, Disconnected, OutOfService, InService
	}

	// 2. Define State Machine Class
	public static class StateMachineFoo extends
			AbstractStateMachine<StateMachineFoo, FSMState, FSMEvent, Integer> {
		protected StateMachineFoo(
				ImmutableState<StateMachineFoo, FSMState, FSMEvent, Integer> initialState,
				Map<FSMState, ImmutableState<StateMachineFoo, FSMState, FSMEvent, Integer>> states) {
			super(initialState, states);
		}

		protected void fromAToB(FSMState from, FSMState to, FSMEvent event,
				Integer context) {
			System.out.println("Transition from '" + from + "' to '" + to
					+ "' on event '" + event + "' with context '" + context
					+ "'.");
		}

		protected void fromBToC(FSMState from, FSMState to, FSMEvent event,
				Integer context) {
			System.out.println("Transition from '" + from + "' to '" + to
					+ "' on event '" + event + "' with context '" + context
					+ "'.");
		}

		protected void ontoB(FSMState from, FSMState to, FSMEvent event,
				Integer context) {
			System.out.println("Entry State \'" + to + "\'.");
		}
	}

	public static void main(String[] args) {
		StateMachineBuilder<StateMachineFoo, FSMState, FSMEvent, Integer> builder = StateMachineBuilderFactory
				.create(StateMachineFoo.class, FSMState.class, FSMEvent.class,
						Integer.class);
		builder.externalTransition().from(FSMState.Idle).to(FSMState.Loading)
				.on(FSMEvent.Download).callMethod("fromAToB");

		builder.externalTransition().from(FSMState.Loading)
				.to(FSMState.Disconnected).on(FSMEvent.ToC)
				.callMethod("fromBToC");
		
		
		builder.externalTransition().from(FSMState.Idle).to(FSMState.Loading).on(FSMEvent.Connected);
		builder.externalTransition().from(FSMState.Loading).to(FSMState.Disconnected).on(FSMEvent.ConnectionClosed);
		builder.externalTransition().from(FSMState.Loading).to(FSMState.InService).on(FSMEvent.LoadSuccess);
		builder.externalTransition().from(FSMState.Loading).to(FSMState.OutOfService).on(FSMEvent.LoadFail);
		builder.externalTransition().from(FSMState.OutOfService).to(FSMState.Disconnected).on(FSMEvent.ConnectionLost);
		builder.externalTransition().from(FSMState.OutOfService).to(FSMState.InService).on(FSMEvent.StartUp);
		builder.externalTransition().from(FSMState.InService).to(FSMState.OutOfService).on(FSMEvent.Shutdown);
		builder.externalTransition().from(FSMState.InService).to(FSMState.Disconnected).on(FSMEvent.ConnectionLost);
		builder.externalTransition().from(FSMState.Disconnected).to(FSMState.InService).on(FSMEvent.ConnectionRestored);
		
		
		builder.onEntry(FSMState.Loading).callMethod("ontoB");

		// 4. Use State Machine
		StateMachineFoo fsm = builder.newStateMachine(FSMState.Idle);
		fsm.fire(FSMEvent.Download, 10);

		System.out.println("Current state is " + fsm.getCurrentState());
		fsm.fire(FSMEvent.ToC, 4);

		System.out.println("Current state is " + fsm.getCurrentState());

		DotVisitor<StateMachineFoo, FSMState, FSMEvent, Integer> visitor = SquirrelProvider
				.getInstance()
				.newInstance(
						new TypeReference<DotVisitor<StateMachineFoo, FSMState, FSMEvent, Integer>>() {
						});

		fsm.accept(visitor);

		visitor.convertDotFile("SquirrelQuickStartStateMachine");

	}

}
