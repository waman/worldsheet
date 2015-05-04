package org.waman.worldsheet

trait StateEvolver[S] extends (S => S)
