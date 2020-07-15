interface Up {
  log: Log;
  compiler: (selector: string, options?: Object, func?: (element: Element) => void) => void;
  validate: (fieldOrSelector: HTMLElement | string, options?: {target: string}) => void
}

interface Log {
  enable: () => void
  disable:
   () => void
}

const up = (window as any).up;
export default up;

