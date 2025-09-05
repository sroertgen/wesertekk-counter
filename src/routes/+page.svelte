<script>
	import { enhance } from '$app/forms';

	let { data, form } = $props();

	let password = $state('');
	let isSubmitting = $state(false);
	/** @type {number | null} */
	let submitTimeout = null;


</script>


<div class="h-screen flex flex-col">
	<div class="navbar bg-base-100">
		<div class="flex-1">
			<span class="btn text-xl btn-ghost">WT Counter</span>
		</div>
		<div class="flex-none">
			<div class="dropdown dropdown-end">
				<div tabindex="0" role="button" class="rounded-btn btn btn-ghost">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						class="inline-block h-5 w-5 stroke-current"
					>
						<path
							stroke-linecap="round"
							stroke-linejoin="round"
							stroke-width="2"
							d="M5 12h.01M12 12h.01M19 12h.01M6 12a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0z"
						>
						</path>
					</svg>
				</div>
				<ul
					tabindex="0"
					class="dropdown-content menu z-[1] mt-4 w-52 rounded-box bg-base-100 p-2 shadow"
				>
					<li>
						<button onclick={() => {
							const modal = /** @type {HTMLDialogElement} */ (document.getElementById('my_modal_1'));
							if (modal) {
								modal.showModal();
							}
						}}>Reset</button>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="flex-1 flex flex-col items-center">
		<h1 class="text-6xl">Gesamt: {data.total}</h1>
		<h1 class="mt-4 text-2xl">Anwesend: {data.onSite}</h1>

		<div class="mt-auto mb-10 flex flex-col items-center gap-4">
			
			<form
				class="w-full"
				method="POST"
				action="?/increment"
				use:enhance={() => {
					if (isSubmitting) return;

					isSubmitting = true;
					if (submitTimeout) {
						clearTimeout(submitTimeout);
					}

					// Reset after 500ms to prevent accidental double-clicks
					submitTimeout = setTimeout(() => {
						isSubmitting = false;
					}, 500);

					return async ({ update }) => {
						await update();
					};
				}}
			>
			
				<button disabled={isSubmitting} class="btn min-h-36 w-full touch-none text-6xl btn-success" type="submit"
					>Neuer Besucher</button
				>
			</form>

			<form
				class="w-full"
				method="POST"
				action="?/reentry"
				use:enhance={() => {
					if (isSubmitting) return;

					isSubmitting = true;
					if (submitTimeout) {
						clearTimeout(submitTimeout);
					}

					// Reset after 500ms to prevent accidental double-clicks
					submitTimeout = setTimeout(() => {
						isSubmitting = false;
					}, 500);

					return async ({ update }) => {
						await update();
					};
				}}
			>
				<button disabled={isSubmitting} class="btn min-h-36 w-full touch-none text-6xl btn-info" type="submit"
					>Wiedereinlass</button
				>
			</form>

			<form
				class="w-full"
				method="POST"
				action="?/decrement"
				use:enhance={() => {
					if (isSubmitting) return;

					isSubmitting = true;
					if (submitTimeout) {
						clearTimeout(submitTimeout);
					}

					// Reset after 500ms to prevent accidental double-clicks
					submitTimeout = setTimeout(() => {
						isSubmitting = false;
					}, 500);

					return async ({ update }) => {
						await update();
					};
				}}
			>
				<button disabled={isSubmitting} class="btn min-h-36 w-full touch-none text-6xl btn-warning" type="submit"
					>Geht</button
				>
			</form>
		</div>
	</div>
</div>

<dialog id="my_modal_1" class="modal">
	<div class="modal-box">
		<h3 class="text-lg font-bold">Reset Counter!</h3>
		<p class="py-4">PW zum Reset eingeben</p>
		<label class="input-bordered input flex items-center gap-2">
			<svg
				xmlns="http://www.w3.org/2000/svg"
				viewBox="0 0 16 16"
				fill="currentColor"
				class="h-4 w-4 opacity-70"
			>
				<path
					fill-rule="evenodd"
					d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
					clip-rule="evenodd"
				/>
			</svg>
			<input id="password" type="password" class="grow" bind:value={password} />
		</label>
		<div class="modal-action">
			<form class="w-full" method="POST" action="?/reset" use:enhance>
				<button
					onclick={() => /** @type {HTMLDialogElement} */ (document.getElementById('my_modal_1')).close()}
					disabled={password !== 'wesertekken'}
					class="btn btn-error"
					type="submit">Zurücksetzen</button
				>
			</form>
			<button class="btn" onclick={() => /** @type {HTMLDialogElement} */ (document.getElementById('my_modal_1')).close()}
				>Close</button
			>
		</div>
	</div>
</dialog>
