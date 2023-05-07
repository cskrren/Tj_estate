<template>
  <page-main>
    <a-card :loading="loading" title="聊天机器人" style="height: 800px;">
      <div class="chat-box">
        <div class="chat-history">
          <div v-for="message in messages" :key="message.id" class="message" :class="{ 'from-bot': message.fromBot }">
            <div class="message-author">{{ message.author }}</div>
            <div class="message-text">{{ message.text }}</div>
          </div>
        </div>
        <div class="input-box">
          <input v-model="newMessage" type="text" placeholder="Type a message..." @keyup.enter="sendMessage">
          <button @click="sendMessage">Send</button>
        </div>
      </div>
    </a-card>
  </page-main>
</template>

<script>

let msg = "";
const decoder = new TextDecoder("utf-8");
const readStream = async (reader,status) => {
  let partialLine = "";

  while (true) {
    // eslint-disable-next-line no-await-in-loop
    const { value, done } = await reader.read();
    if (done) break;

    const decodedText = decoder.decode(value, { stream: true });

    if (status !== 200) {
      const json = JSON.parse(decodedText); // start with "data: "
      const content = json.error.message ?? decodedText;
      msg += content;
      return;
    }

    const chunk = partialLine + decodedText;
    const newLines = chunk.split(/\r?\n/);

    partialLine = newLines.pop() ?? "";

    for (const line of newLines) {
      if (line.length === 0) continue; // ignore empty message
      if (line.startsWith(":")) continue; // ignore sse comment message
      if (line === "data: [DONE]") return; //

      const json = JSON.parse(line.substring(6)); // start with "data: "
      const content =
        status === 200
          ? json.choices[0].delta.content ?? ""
          : json.error.message;
      msg += content;
    }
  }
};

export default {
  data() {
    return {
      newMessage: '',
      messages: [
        {
          id: 1,
          author: 'Bot',
          text: 'Hello, how can I help you?',
          fromBot: true
        }
      ],
      history: [],
    }
  },
  methods: {
    async sendMessage() {
      if (!this.newMessage) {
        return;
      }
      const message = {
        id: this.messages.length + 1,
        author: 'You',
        text: this.newMessage,
        fromBot: false
      };
      this.messages.push(message);
      this.history.push({
        "role": "user", 
        "content": this.newMessage
      });
      try {
        const {body,status} = await fetch("https://api.openai.com/v1/chat/completions", {
          method: "post",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer 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`,
          },
          body: JSON.stringify({
            model: "gpt-3.5-turbo",
            stream: true,
            messages: this.history,
            temperature: this.temperature,
          }),
        });
        if (body) {
          const reader = body.getReader();
          await readStream(reader, status, this.botMessage);
        }
        const message = {
          id: this.messages.length + 1,
          author: 'Bot',
          text: msg,
          fromBot: true
        };
        this.messages.push(message);
        this.history.push({
          "role": "system", 
          "content": msg
        });
      } catch (error) {
        const message = {
          id: this.messages.length + 1,
          author: 'Bot',
          text: 'Sorry, I am not available right now. Please try again later.',
          fromBot: true
        }; 
      }
      this.newMessage = '';
      msg = '';
    }
  }
};
</script>
<style>
.chat-box {
    height: 100%;
    width: auto;
    border: 1px solid #ccc;
    border-radius: 5px;
    overflow: hidden;
    display: flex;
    flex-direction: column;
}
.chat-history {
    flex: 1;
    overflow-y: scroll;
    padding: 10px;
}
.message {
    display: flex;
    flex-direction: column;
    margin-bottom: 10px;
    padding: 10px;
    border-radius: 5px;
}
.message-author {
    font-weight: bold;
    margin-bottom: 5px;
}
.message-text {
    font-size: 14px;
}
.from-bot {
    align-self: flex-start;
    background-color: #f5f5f5;
}
.input-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #f5f5f5;
    padding: 5px;
}
.input-box input {
    flex: 1;
    height: 40px;
    border-radius: 20px;
    border: none;
    padding: 0 20px;
    font-size: 14px;
}
.input-box button {
    height: 40px;
    border-radius: 20px;
    border: none;
    background-color: #007bff;
    font: 14px;
    color: #fff;
}
</style>
