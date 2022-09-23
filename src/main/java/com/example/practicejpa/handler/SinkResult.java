package com.example.practicejpa.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SinkResult{
	String id;
	SSESink chatSink;
}