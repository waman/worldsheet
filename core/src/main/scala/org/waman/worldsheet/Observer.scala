package org.waman.worldsheet

trait Observer[S, D] extends (S => D)