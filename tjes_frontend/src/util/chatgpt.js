import axios from 'axios';

const API_KEY = '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';//替换成你申请的open AI key
const MODEL = 'gpt3.5-';//使用的语言模型

const api = axios.create({
  baseURL: 'https://api.openai.com/v1/',
  headers: {
    Authorization: `Bearer ${API_KEY}`,
    'Content-Type': 'application/json',
  },
});

async function generateText(prompt) {
  const response = await api.post('/engines/text-davinci-002/completions', {
    prompt: prompt,//问的问题
    max_tokens: 1024,
    n: 1,
    stop: '\n',
  });
  return response.data.choices[0].text.trim();
}

export default {
  generateText,
};