package types

type Event[Type any] struct {
	Payload Type `json:"payload"`
}
