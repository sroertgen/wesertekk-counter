import adapter from '@sveltejs/adapter-node';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	kit: {
		adapter: adapter({
			precompress: true,
			port: 3000,
			host: '0.0.0.0'
		})
	}
};

export default config;
