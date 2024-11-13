<script lang="ts">
    import {webSocket} from "rxjs/webSocket";

    const chat = webSocket({
        url: "wss://pm1eadpmvl.execute-api.us-east-1.amazonaws.com/Prod/",
        deserializer: (messageEvent) => messageEvent.data,
    });
    const messages: string[] = $state([]);
    let active = $state(true);
    let newMessage = $state("");

    chat.subscribe({
        next: msg => {
            messages.push(msg);
        },
        error: err => {
            active = false
            console.error(err);
        },
        complete: () => active = false
    });

    function sendMessage() {
        chat.next({
            action: "sendmessage",
            data: newMessage
        });
        newMessage = "";
    }
</script>

<main class="flex flex-col gap-2 m-2">
    <div class="flex gap-2">
        <h1>Live Chat</h1>
        <span class="{active ? 'bg-green-600' : 'bg-red-600'} p-1 rounded">{active ? 'Connected' : 'Disconnected'}</span>
    </div>
    <ul class="flex flex-col gap-1">
        {#each messages as message}
            <li class="bg-gray-500 rounded px-1">{message}</li>
        {/each}
    </ul>
    <input
            bind:value={newMessage}
            class="border p-2 rounded"
            onkeydown={(e) => e.key === 'Enter' && sendMessage()}
            placeholder="Type a message"
            type="text"
    />
</main>
