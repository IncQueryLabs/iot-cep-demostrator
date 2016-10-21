package com.incquerylabs.iot.leapmotion.viatracep.vepl.patterns.atomic.queryresult;

import com.incquerylabs.iot.leapmotion.lmemf.Gesture;
import com.incquerylabs.iot.leapmotion.viatracep.vepl.events.queryresult.SPEED_UP_RIGHT_Event;
import org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl;

@SuppressWarnings("all")
public class SPEED_UP_RIGHT_Pattern extends AtomicEventPatternImpl {
  private Gesture gesture;
  
  public SPEED_UP_RIGHT_Pattern() {
    super();
    setType(SPEED_UP_RIGHT_Event.class.getCanonicalName());
    setId("com.incquerylabs.iot.leapmotion.viatracep.vepl.patterns.atomic.queryresult.speed_up_right_pattern");
  }
}
